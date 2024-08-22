package com.zhenshu.common.enums.kg.health.health;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/22 10:02
 * @desc 班级绑定老师枚举
 */
@AllArgsConstructor
public enum StandardType implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 身高
     */
    HEIGHT(0, "身高"),
    /**
     * 体重
     */
    WEIGHT(1, "体重");

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
