package com.zhenshu.system.business.kg.home.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/9 14:32
 * @desc 人数金额出参
 */
@Data
@ApiModel(description = "人数金额出参")
public class KgHomePeopleAmountBO {
    /**
     * 人数
     */
    @ApiModelProperty(value = "人数")
    private Integer count;

    /**
     * 支出金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;
}
