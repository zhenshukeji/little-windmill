package com.zhenshu.common.enums.kg.work.educational;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/3/31 15:57
 * @desc 微课件状态枚举
 */
@AllArgsConstructor
public enum CoursewareStatus implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 0上架 1下架
     */
    PUTAWAY(0, "上架"),

    SOLD_OUT(1, "下架");

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
