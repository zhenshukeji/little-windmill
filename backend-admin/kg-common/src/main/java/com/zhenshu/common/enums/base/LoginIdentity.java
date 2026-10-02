package com.zhenshu.common.enums.base;

/**
 * @author Jing
 */
public enum LoginIdentity {
    /**
     * 登录人员类型 0平台人员 1集团管理员 2集团人员 3园区管理员 4园区人员 5集团人员进入园区
     */
    PLATFORM(0, "平台人员"),
    BLOC_ADMIN(1, "集团管理员"),
    BLOC_PEOPLE(2, "集团人员"),
    KG_ADMIN(3, "园区管理员"),
    KG_PEOPLE(4, "园区人员"),
    BLOC_TO_KG(5, "集团人员进入园区");

    private final Integer code;
    private final String info;

    LoginIdentity(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public Integer getValue() {
        return code;
    }

    /**
     * 判断是否是集团员工
     *
     * @param identity 身份
     * @return 结果
     */
    public static boolean isBloc(LoginIdentity identity){
        return identity == BLOC_ADMIN || identity == BLOC_PEOPLE;
    }

    /**
     * 判断是否是校区员工
     *
     * @param identity 身份
     * @return 结果
     */
    public static boolean isKg(LoginIdentity identity){
        return identity == KG_ADMIN || identity == KG_PEOPLE || identity == BLOC_TO_KG;
    }
}
