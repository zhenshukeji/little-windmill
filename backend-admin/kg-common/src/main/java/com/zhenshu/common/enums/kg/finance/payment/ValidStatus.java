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
public enum ValidStatus implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 有效状态 0有效 1作废
     */
    EFFICIENT(0, "有效"),
    INVALID(1, "作废");

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
