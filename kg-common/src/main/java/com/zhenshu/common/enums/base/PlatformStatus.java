package com.zhenshu.common.enums.base;


import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author Jing
 */
public enum PlatformStatus implements IEnum<Integer> {
    /**
     * 平台人员状态 0以平台身份 1集团管理员 3校区管理员
     */
    PLATFORM(0, "以平台身份"), INTO_BLOC(1, "以集团身份"), INTO_KG(2, "进入校区的身份");

    private final Integer value;
    private final String info;

    PlatformStatus(Integer value, String info) {
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
