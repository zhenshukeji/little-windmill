package com.zhenshu.parent.common.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.parent.common.library.knife4j.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/1 15:37
 * @desc
 */
@Getter
@AllArgsConstructor
public enum PayStatus implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 支付状态 0支付中 1支付成功 2支付失败
     */
    PAYMENT(0, "支付中"),
    SUCCESS(1, "支付成功"),
    FAIL(2, "支付失败");

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
