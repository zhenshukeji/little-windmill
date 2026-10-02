package com.zhenshu.parent.common.constant.exception;


import com.zhenshu.parent.common.constant.enums.ErrorEnums;

/**
 * 自定义异常
 *
 * @author zhenshu
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(ErrorEnums errorEnums){
        this.message = errorEnums.getMsg();
        this.code = errorEnums.getCode();
    }

    public ServiceException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public ServiceException(String message, Throwable e) {
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
