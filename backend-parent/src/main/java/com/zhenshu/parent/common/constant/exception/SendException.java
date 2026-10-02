package com.zhenshu.parent.common.constant.exception;


import com.zhenshu.parent.common.constant.enums.ErrorEnums;

/**
 * 会发送消息的异常信息
 *
 * @author zhenshu
 */
public class SendException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public SendException(String message) {
        this.message = message;
    }

    public SendException(ErrorEnums errorEnums){
        this.message = errorEnums.getMsg();
        this.code = errorEnums.getCode();
    }

    public SendException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public SendException(String message, Throwable e) {
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
