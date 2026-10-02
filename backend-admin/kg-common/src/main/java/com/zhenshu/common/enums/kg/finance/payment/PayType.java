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
public enum PayType implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 支付类型 0微信支付 1支付宝 2平安银行 3线下刷卡 4线下微信 5线下支付宝 6其他
     */
    WX_PAY(0, "微信支付"),
    ALI_PAY(1, "支付宝"),
    PINGAN_BANK(2, "平安银行"),
    SWIPE_CARD(3, "线下刷卡"),
    UNDER_LINE_WX(4, "线下微信"),
    UNDER_LINE_ALI(5, "线下支付宝"),
    OTHER(6, "其他"),
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
