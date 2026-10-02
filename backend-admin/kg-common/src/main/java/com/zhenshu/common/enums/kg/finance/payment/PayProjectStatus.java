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
public enum PayProjectStatus implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 支付配置项目状态 0未开始 1进行中 2已结束
     */
    UN_START(0, "未开始"),
    IN_PROGRESS(1, "进行中"),
    OVER(2, "已结束"),
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
