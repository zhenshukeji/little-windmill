package com.zhenshu.common.enums.kg.work.checking;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author zch
 * @version 1.0
 * @date 2022/3/7 10:56
 * @desc 体温测量时间
 */
@AllArgsConstructor
public enum StudentMeasureTime implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 上午
     */
    AM(0, "上午"),

    /**
     * 事假
     */
    PM(1, "下午");

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
