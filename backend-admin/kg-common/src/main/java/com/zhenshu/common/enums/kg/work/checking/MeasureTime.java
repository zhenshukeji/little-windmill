package com.zhenshu.common.enums.kg.work.checking;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/3/7 10:56
 * @desc 体温测量时间
 */
@AllArgsConstructor
public enum MeasureTime implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 上午
     */
    AM(0, "上午"),

    /**
     * 事假
     */
    PM(1, "下午"),

    /**
     * 中午第一次
     */
    NOON_ONE(2, "中午第一次"),

    /**
     * 中午第二次
     */
    NOON_TWO(3, "中午第二次");

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
