package com.zhenshu.parent.common.config.aspect.log;

import com.zhenshu.parent.common.constant.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

/**
 * @author jing
 * @version 1.0
 * @desc 接口消耗时间打印
 * @date 2021/2/19 0019 11:05
 **/
@Aspect
@Service
public class LogAspect {


    @Pointcut("@annotation(com.zhenshu.parent.common.config.aspect.log.LogFunction)")
    private void lockMethod() {
    }

    private Log logger = LogFactory.getLog(LogAspect.class);


    /**
     * 使用@around 环绕通知=前置+目标方法执行+后置通知
     */
    @Around("lockMethod()")
    public Object logUseTime(ProceedingJoinPoint joinPoint) {
        // 获取方法参数值数组
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();
        // 增加访问次
        // 方法结束后，删除缓存
        Object object;
        try {
            object = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            logger.error(throwable);
            // 为了正常返回状态码
            if (throwable instanceof ServiceException) {
                // 只有状态码不为空的时候才会正常返回状态码
                if (((ServiceException) throwable).getCode() != null) {
                    throw new ServiceException(throwable.getMessage(), ((ServiceException) throwable).getCode());
                }
            }
            // 写在finally 的原因是改方法可能会抛出异常
            throw new ServiceException("系统异常，请稍后重试", throwable);
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info(String.format("interface: %s, use_time: %d ms", methodName, endTime - startTime));
        }
        return object;
    }

}
