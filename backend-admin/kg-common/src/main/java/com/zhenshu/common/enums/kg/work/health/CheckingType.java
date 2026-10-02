package com.zhenshu.common.enums.kg.work.health;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-27 11:00
 * @desc 日常检查类型
 */
@AllArgsConstructor
public enum CheckingType implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 晨检
     */
    MORNING_CHECK(0, "晨检"),

    /**
     * 午检
     */
    MIDDAY_CHECK(1, "午检");

    /**
     * 实际值
     */
    private Integer code;
    /**
     * 描述
     */
    private String info;

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

