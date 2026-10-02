package com.zhenshu.common.enums.kg.base.advanced;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @Author xuzq
 * @Date 2022/9/28 18:39
 * @Version 1.0
 */
@AllArgsConstructor
public enum PlaceType implements IBaseEnum<Integer>, IEnum<Integer> {



    /**
     * 预约未满
     */
    INDOOR(0, "户内"),

    /**
     * 预约已满
     */
    OUTDOOR(1, "户外");

    /**
     * 实际值
     */
    private final int code;

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
