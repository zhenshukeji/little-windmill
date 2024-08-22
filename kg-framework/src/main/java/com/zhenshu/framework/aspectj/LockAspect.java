package com.zhenshu.framework.aspectj;

import com.zhenshu.common.annotation.LockFunction;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.jackson.JacksonUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jing
 * @version 1.0
 * @desc 防重点的切片拦截
 * @date 2021/2/19 0019 11:05
 **/
@Aspect
@Component
public class LockAspect {


    @Pointcut("@annotation(com.zhenshu.common.annotation.LockFunction)")
    private void lockMethod() {
    }

    @Resource
    private RedissonClient redisson;
    @Value("${spring.redis.cache_name}")
    public String cacheName;

    private Log logger = LogFactory.getLog(LockAspect.class);

    private static final String NULL_VALUE = "null";


    /**
     * 使用@around 环绕通知=前置+目标方法执行+后置通知
     */
    @Around("lockMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取注解上的传参
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LockFunction annotation = signature.getMethod().getAnnotation(LockFunction.class);
        // 获取方法参数值数组
        Object[] args = joinPoint.getArgs();
        // 增加访问次数
        Object arg;
        if (annotation.fromJwt()) {
            arg = SecurityUtils.getLoginUser();
        } else {
            arg = ArrayUtils.isNotEmpty(args) ? args[Constants.ZERO] : null;
        }
        RLock lock = this.tryLock(annotation, arg);
        // 方法结束后，删除缓存
        Object object = null;
        try {
            object = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw throwable;
        } finally {
            lock.unlock();
        }
        return object;
    }

    /**
     * 尝试获取锁
     *
     * @param annotation 注解对象
     * @param arg        参数
     * @return 锁对象
     */
    private RLock tryLock(LockFunction annotation, Object arg) {
        String methodName = annotation.methodName();
        String className = annotation.className();
        String key = null;
        // 判断入参是否为一个对象，是的话从json里取值
        if (annotation.isObj()) {
            if (StringUtils.isNotEmpty(annotation.keyName())) {
                String body = JacksonUtil.toJson(arg);
                key = JacksonUtil.parseString(body, annotation.keyName());
            }
        } else {
            // 如果不是对象的话，那么入参直接转成string
            key = String.valueOf(arg);
        }
        // 判断key值是否为空，为空的话抛异常
        if (StringUtils.isEmpty(key) || NULL_VALUE.equals(key)) {
            throw new ServiceException(ErrorEnums.LOCK_EMPTY_PARAM);
        }
        // 自增缓存，查看是否有同时操作的用户
        String cacheKey = String.format("%s:lock:%s:%s_%s", cacheName, className, methodName, key);
        RLock lock = redisson.getLock(cacheKey);
        boolean result = lock.tryLock();
        if (!result) {
            throw new ServiceException(ErrorEnums.FREQUENT_OPERATION);
        }
        return lock;
    }

}
