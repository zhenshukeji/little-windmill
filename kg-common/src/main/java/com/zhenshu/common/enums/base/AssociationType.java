package com.zhenshu.common.enums.base;


import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author Jing
 */
public enum AssociationType implements IEnum<Integer> {
    /**
     * 关联类型 0平台人员 1集团人员 2园区人员
     */
    PLATFORM(0, "平台人员"),
    BLOC(1, "集团人员"),
    KG(2, "园区人员");

    private final Integer value;
    private final String info;

    AssociationType(Integer value, String info) {
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
