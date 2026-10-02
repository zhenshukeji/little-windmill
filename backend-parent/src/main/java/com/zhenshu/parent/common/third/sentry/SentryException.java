package com.zhenshu.parent.common.third.sentry;


import com.zhenshu.parent.common.constant.enums.ErrorEnums;

/**
 * @author jing
 * @version 1.0
 * @desc sentry 发送异常
 * @date 2021/2/18 0018 18:45
 **/
public class SentryException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public SentryException(String message) {
        this.message = message;
    }

    public SentryException(ErrorEnums errorEnums) {
        this.message = errorEnums.getMsg();
        this.code = errorEnums.getCode();
    }

    public SentryException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public SentryException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }


}
