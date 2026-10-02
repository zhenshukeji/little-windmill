package com.zhenshu.parent.common.config.aspect.page;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.zhenshu.parent.common.constant.exception.ServiceException;
import com.zhenshu.parent.common.utils.jackson.JacksonUtil;
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
 * @desc 分页拦截器
 * @date 2021/8/3 0003 15:50
 **/
@Aspect
@Service
public class PageAspect {


    @Pointcut("@annotation(com.zhenshu.parent.common.config.aspect.page.StartPage)")
    private void method() {
    }

    private Log logger = LogFactory.getLog(PageAspect.class);

    /**
     * 使用@around 环绕通知=前置+目标方法执行+后置通知
     */
    @Around("method()")
    public Object run(ProceedingJoinPoint joinPoint) {
        // 获取方法参数值数组
        Object[] args = joinPoint.getArgs();
        // 方法结束后，删除缓存
        Object object;
        try {
            startPage(joinPoint, args[0]);
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
            PageHelper.clearPage();
        }
        return object;
    }

    /**
     * 前置方法，记录访问次数
     *
     * @param joinPoint 切点
     * @param arg       参数
     */
    private void startPage(ProceedingJoinPoint joinPoint, Object arg) {
        // 获取token
        String body = JacksonUtil.toJson(arg);
        Integer pageNum = JacksonUtil.parseInteger(body, "pageNum");
        Integer pageSize = JacksonUtil.parseInteger(body, "pageSize");
        if (ObjectUtil.isNotNull(pageNum) && ObjectUtil.isNotNull(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
        }
    }

}
