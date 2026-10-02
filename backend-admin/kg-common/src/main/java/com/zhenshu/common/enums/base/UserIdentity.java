package com.zhenshu.common.enums.base;


import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author Jing
 */
public enum UserIdentity implements IEnum<Integer> {
    /**
     * 身份 0-表示管理员 1-普通人员
     */
    ADMIN(0, "管理员"),
    PEOPLE(1, "普通员工");

    private final Integer value;
    private final String info;

    UserIdentity(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public Integer getValue() {
        return value;
    }

}
