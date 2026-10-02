package com.zhenshu.system.business.kg.home.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/9 14:32
 * @desc 首页金额出参
 */
@Data
@ApiModel(description = "首页金额出参")
public class KgHomeDayAmountBO {
    /**
     * 收入金额
     */
    @ApiModelProperty(value = "收入金额")
    private BigDecimal income;

    /**
     * 支出金额
     */
    @ApiModelProperty(value = "支出金额")
    private BigDecimal expend;
}
