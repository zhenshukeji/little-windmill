package com.zhenshu.common.enums.base;


import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author Jing
 */
public enum BlocStaffStatus implements IEnum<Integer> {
    /**
     * 集团人员状态 0以集团身份 1进入校区的身份
     */
    BLOC(0, "以集团身份"), INTO_KG(1, "进入校区的身份");

    private final Integer value;
    private final String info;

    BlocStaffStatus(Integer value, String info) {
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
