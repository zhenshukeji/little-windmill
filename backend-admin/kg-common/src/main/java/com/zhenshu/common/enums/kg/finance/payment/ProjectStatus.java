package com.zhenshu.common.enums.kg.finance.payment;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author jing
 * @version 1.0
 * @desc 缴费管理-审核状态
 * @date 2022/2/16 0016 10:05
 **/
@AllArgsConstructor
public enum ProjectStatus implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 项目状态 0审核中 1通过 2未通过 3部分缴费 4完成缴费 5作废
     */
    UNDER_REVIEW(0, "审核中"),
    PASS(1, "通过"),
    REJECT(2, "未通过"),
    PARTIAL_PAYMENT(3, "部分缴费"),
    COMPLETE_PAYMENT(4, "完成缴费"),
    INVALID(5, "作废"),
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
