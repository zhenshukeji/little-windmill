package com.zhenshu.parent.common.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.parent.common.library.knife4j.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author zch
 * @version 1.0
 * @desc 数据是否为new-数据状态
 * @date 2022-06-23
 **/
@AllArgsConstructor
public enum DataStatus implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 数据状态 0新数据 1旧数据
     */
    NEW(0, "新数据"),
    OLD(1, "旧数据");

    private final int code;
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
