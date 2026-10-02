package com.zhenshu.parent.common.constant.exception;

/**
 * @author jing
 * @version 1.0
 * @desc 通知失败
 * @date 2022/5/10 0010 15:02
 **/
public class WxNotifyFailException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 空构造方法，避免反序列化问题
     */
    public WxNotifyFailException() {
    }

    public WxNotifyFailException(String msg) {
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
