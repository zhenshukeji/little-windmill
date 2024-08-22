package com.zhenshu.common.enums.kg.finance.payment;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author jing
 * @version 1.0
 * @desc 退款方式
 * @date 2022/2/21 0021 11:33
 **/
@AllArgsConstructor
public enum RefundWay implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 退款方式 0线下退款 1原路退回
     */
    UNDER_LINE(0, "线下退款"),
    ORIGINAL_ROAD(1, "原路退回"),
    ;

    private Integer code;
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
