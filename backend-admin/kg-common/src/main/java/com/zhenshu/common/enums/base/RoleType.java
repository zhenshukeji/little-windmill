package com.zhenshu.common.enums.base;


import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author Jing
 */
public enum RoleType implements IEnum<Integer> {
    /**
     * 角色类型 0-系统角色 1-集团角色 2-园区角色
     */
    SYSTEM(0, "系统角色"),
    BLOC(1, "集团角色"),
    KG(2, "园区角色"),
    ;

    private final Integer value;
    private final String info;

    RoleType(Integer value, String info) {
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
