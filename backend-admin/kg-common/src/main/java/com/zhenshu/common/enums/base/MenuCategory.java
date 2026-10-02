package com.zhenshu.common.enums.base;


import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;

/**
 * @author Jing
 */
public enum MenuCategory implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 菜单类别 0-系统菜单 1-平台菜单 2-集团菜单 3-园区菜单
     */
    ADMIN(0, "系统菜单"),
    PLATFORM(1, "平台菜单"),
    BLOC(2, "集团菜单"),
    KG(3, "园区菜单"),
    ;

    private final Integer value;
    private final String info;

    MenuCategory(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    @Override
    public Integer getCode() {
        return value;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + "-" + info;
    }

}
