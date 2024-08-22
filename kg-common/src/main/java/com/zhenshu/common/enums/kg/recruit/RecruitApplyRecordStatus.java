package com.zhenshu.common.enums.kg.recruit;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/23 9:39
 * @desc
 */
@Getter
@AllArgsConstructor
public enum RecruitApplyRecordStatus implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 状态 0未缴费 1已缴费 2部分退费 3已退费
     */
    UNPAID(0, "未缴费"),
    PAID(1, "已缴费"),
    PARTIAL_REFUND(2, "部分退费"),
    FULL_REFUND(3, "已退费"),
    ;

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
