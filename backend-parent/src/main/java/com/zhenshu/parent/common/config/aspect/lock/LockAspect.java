package com.zhenshu.parent.common.config.aspect.lock;

import com.zhenshu.parent.common.constant.Constants;
import com.zhenshu.parent.common.constant.enums.ErrorEnums;
import com.zhenshu.parent.common.constant.exception.ServiceException;
import com.zhenshu.parent.common.utils.jackson.JacksonUtil;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jing
 * @version 1.0
 * @desc 防重点的切片拦截
 * @date 2021/2/19 0019 11:05
 **/
@Aspect
@Service
public class LockAspect {


    @Pointcut("@annotation(com.zhenshu.parent.common.config.aspect.lock.LockFunction)")
    private void lockMethod() {
    }

    @Resource
    private RedissonClient redisson;
    @Value("${spring.redis.cache_name}")
    public String cacheName;

    private Log logger = LogFactory.getLog(LockAspect.class);


    /**
     * 使用@around 环绕通知=前置+目标方法执行+后置通知
     */
    @Around("lockMethod()")
    public Object getCache(ProceedingJoinPoint joinPoint) {
        // 获取方法参数值数组
        Object[] args = joinPoint.getArgs();
        // 方法结束后，删除缓存
        Object object;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LockFunction annotation = signature.getMethod().getAnnotation(LockFunction.class);
        // 获取方法参数值数组
        RLock lock = this.tryLock(annotation, args[Constants.ZERO]);
        try {
            object = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            logger.error("分布式锁在执行方法时出现异常，异常信息是", throwable);
            if (throwable instanceof ServiceException) {
                // 只有状态码不为空的时候才会正常返回状态码
                if (((ServiceException) throwable).getCode() != null) {
                    throw new ServiceException(throwable.getMessage(), ((ServiceException) throwable).getCode());
                }
            }
            throw new ServiceException(ErrorEnums.INNER_ERROR);
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
        // 获取token
        String body = JacksonUtil.toJson(arg);
        String key = JacksonUtil.parseString(body, annotation.keyName());
        if (StringUtils.isEmpty(key)) {
            throw new ServiceException(ErrorEnums.PARAM_EMPTY);
        }
        // 自增缓存，查看是否有同时操作的用户
        String cacheKey = String.format("%s:lock:%s_%s", cacheName, methodName, key);
        RLock lock = redisson.getLock(cacheKey);
        boolean result = lock.tryLock();
        if (!result) {
            throw new ServiceException(ErrorEnums.FREQUENT_OPERATION);
        }
        return lock;
    }


}
