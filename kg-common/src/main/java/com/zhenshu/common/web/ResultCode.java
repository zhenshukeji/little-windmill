package com.zhenshu.common.web;

import com.zhenshu.common.constant.HttpStatus;

public enum ResultCode {

    /* 成功状态码 */
    SUCCESS(HttpStatus.SUCCESS, "成功"),

    /* 失败状态码 */
    FAIL(HttpStatus.BAD_METHOD, "失败"),

    /* 业务400错误 */
    LOGIN_ERROR(400100, "用户名/密码错误"),

    /* 未授权 */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "请重新登录"),

    /* 参数不能为空 */
    PARAM_EMPTY(40301, "参数不能为空");


    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

}


