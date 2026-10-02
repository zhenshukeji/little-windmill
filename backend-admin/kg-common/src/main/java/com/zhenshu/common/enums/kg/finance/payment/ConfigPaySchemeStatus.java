package com.zhenshu.common.enums.kg.finance.payment;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author jing
 * @version 1.0
 * @desc 缴费管理-有效状态
 * @date 2022/2/16 0016 10:05
 **/
@AllArgsConstructor
public enum ConfigPaySchemeStatus implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 状态 0禁用 1启用
     */
    INVALID(0, "禁用"),
    VALID(1, "启用");

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
