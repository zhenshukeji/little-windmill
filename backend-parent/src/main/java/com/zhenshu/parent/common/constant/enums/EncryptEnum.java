package com.zhenshu.parent.common.constant.enums;

/**
 * @author jing
 * @version 1.0
 * @desc 加解密枚举类
 * @date 2021/2/7 0007 11:46
 **/
public enum EncryptEnum {

    /**
     * 配置密文里的参数
     */
    TOEKN("token","token密文里的类型字段"),
    SHARE("share","分享携带的id密文字段")
    ;

    private String code;

    private String msg;

    EncryptEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
