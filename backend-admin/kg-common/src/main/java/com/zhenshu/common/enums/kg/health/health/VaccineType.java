package com.zhenshu.common.enums.kg.health.health;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/22 10:02
 * @desc 疫苗类型枚举
 */
@AllArgsConstructor
public enum VaccineType implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 卡介苗
     */
    BCG(0, "卡介苗"),

    /**
     * 百白破
     */
    DPT(1, "百白破"),

    /**
     * 糖丸
     */
    tOPV(2, "糖丸"),

    /**
     * 麻疹
     */
    MEASLES(3, "麻疹"),

    /**
     * 乙肝
     */
    HBV(4, "乙肝"),

    /**
     * 乙脑
     */
    MENINGITIS_B(5, "乙脑"),

    /**
     * 白破
     */
    DTap(6, "白破");

    /**
     * 实际值
     */
    private final Integer code;
    /**
     * 描述
     */
    private final String info;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public Integer getValue() {
        return code;
    }

    @Override
    public String toString() {
        return code + "-" + info;
    }
}
