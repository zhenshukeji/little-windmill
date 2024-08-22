package com.zhenshu.common.enums.kg.base.recruit;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/13 19:51
 * @desc 招生信息状态
 */
@AllArgsConstructor
public enum  RecruitStatus implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 启用
     */
    ENABLE(0, "启用"),

    /**
     * 禁用
     */
    DISABLE(1, "禁用");

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
