package com.zhenshu.parent.common.constant.exception;


import cn.hutool.core.util.ObjectUtil;
import com.zhenshu.parent.common.constant.HttpStatus;
import com.zhenshu.parent.common.third.sentry.SentryUtils;
import com.zhenshu.parent.common.constant.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author zhenshu
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Resource
    private SentryUtils sentryUtils;

    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public Result<String> baseException(BaseException e, HttpServletRequest request) {
        logger.warn(String.format("interface %s , baseException", request.getRequestURI()), e);
        sentryUtils.sendException(e);
        return new Result<String>().fail("系统异常，请稍后重试");
    }

    /**
     * 参数错误处理友好返回
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        logger.warn(String.format("interface %s ， MethodArgumentNotValidException %s", request.getRequestURI(), e.getMessage()));
        return new Result<String>().fail(HttpStatus.BAD_REQUEST, "必传参数不能为空");
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public Result<Object> customerException(ServiceException e, HttpServletRequest request) {
        if (ObjectUtil.isNull(e.getCode())) {
            return new Result<>().fail(e.getMessage());
        }
        return new Result<>().fail(e.getCode(), e.getMessage());
    }


    /**
     * 业务异常
     */
    @ExceptionHandler(SendException.class)
    public Result<Object> sendException(SendException e, HttpServletRequest request) {
        logger.error(String.format("interface %s , sendException ", request.getRequestURI()), e);
        sentryUtils.sendException(e);
        if (ObjectUtil.isNull(e.getCode())) {
            return new Result<>().fail("系统异常，请稍后重试");
        }
        return new Result<>().fail(e.getCode(), "系统异常，请稍后重试");
    }


    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e, HttpServletRequest request) {
        logger.error(String.format("interface %s , Exception ", request.getRequestURI()), e);
        sentryUtils.sendException(e);
        return new Result<>().fail("系统异常，请稍后重试");
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public Result<Object> validatedBindException(BindException e, HttpServletRequest request) {
        logger.error(String.format("interface %s , BindException ", request.getRequestURI()), e);
        sentryUtils.sendException(e);
        return new Result<>().fail("系统异常，请稍后重试");
    }

}
