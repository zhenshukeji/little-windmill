package com.zhenshu.parent.common.constant.enums;

import com.zhenshu.parent.common.constant.HttpStatus;

/**
 * @author jing
 * @version 1.0
 * @desc 返回类
 * @date 2020/12/17 0017 19:41
 **/
public enum ErrorEnums {

    /**
     * 返回数据
     */
    SUCCESS(HttpStatus.SUCCESS, "成功"),
    FAIL(HttpStatus.BAD_REQUEST, "请求方式与预期不一致"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "请重新登录"),
    LOGIN_ERROR(HttpStatus.BAD_REQUEST, "用户名/密码错误"),
    INNER_ERROR(HttpStatus.ERROR, "参与人数过多，请稍后重试"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "参数校验失败"),
    DATA_NOT_EXIST(40001, "数据不存在"),

    // 业务错误 4xx
    PARAM_EMPTY(40301, "参数不能为空"),
    BAD_PARAM(40302, "参数错误或者格式不正确"),
    FREQUENT_OPERATION(40312, "您操作得太快了"),
    IMAGE_NOT_GREEN(40336, "上传的图片有违规内容"),
    TEXT_NOT_GREEN(40337, "上传的文本有违规内容"),
    IMAGE_CHECK_ERROR(40338, "图片校验失败，请稍后重试"),
    TEXT_CHECK_ERROR(40339, "文本校验失败，请稍后重试"),
    CODE_INVALID(40404, "code 已被使用或者不正确，换取openid失败"),
    CHANGE_PHONE_ERROR(40405, "换取手机号失败，请退出重试"),
    AUTH_CAPTCHA_UN_MATCH(40406, "验证码错误"),
    PHONE_NOT_EXIST(40407, "该手机号对应的学生不存在，请联系所在校区的老师核对"),
    NOT_BIND_STUDENT(40408, "绑定学生错误，不存在"),
    NOT_PAY(40409, "缴费订单状态错误，不可支付"),
    PAYMENT_DEAL_NOT_EXIST(40410, "缴费交易不存在"),
    PAYMENT_DEAL_CANNOT_PAY(40411, "已缴费, 不可重复缴费"),
    ALREADY_PAY(40412, "已支付"),
    PAY_ERROR(40413, "调起支付失败，请稍后重试"),
    QUERY_ERROR(40414, "查询订单失败，请稍后重试"),
    PAYMENT_DEAL_NO_PAY(40415, "未缴费, 不可查看"),
    ID_NOT_FOUND(40416, "id查不到对应数据"),
    UPDATE_FAIL(40417, "修改数据失败，数据不存在或出现异常"),
    GET_FAIL(40417, "获取数据失败，数据不存在或出现异常"),

    // 系统错误 5xx
    BIZ_ERROR_CODE(50300, "操作错误"),
    ;

    ErrorEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
