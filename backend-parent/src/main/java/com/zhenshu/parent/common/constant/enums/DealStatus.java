package com.zhenshu.parent.common.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.parent.common.library.knife4j.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jing
 * @version 1.0
 * @desc 缴费管理-有效状态
 * @date 2022/2/16 0016 10:05
 **/
@Getter
@AllArgsConstructor
public enum DealStatus implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 状态 0未缴费 1已缴费 2部分退费 3已退费 4作废
     */
    UNPAID(0, "未缴费"),
    PAID(1, "已缴费"),
    PARTIAL_REFUND(2, "部分退费"),
    FULL_REFUND(3, "已退费"),
    INVALID(4, "作废");

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
