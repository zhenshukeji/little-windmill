package com.zhenshu.common.enums.kg.finance.payment;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author jing
 * @version 1.0
 * @desc 支付类型
 * @date 2022/2/21 0021 11:33
 **/
@AllArgsConstructor
public enum PayWay implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 支付方式 0线上 1线下
     */
    ON_LINE(0, "线上缴费"),
    UNDER_LINE(1, "线下缴费"),
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
