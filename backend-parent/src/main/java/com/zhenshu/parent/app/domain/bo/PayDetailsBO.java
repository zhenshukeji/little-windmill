package com.zhenshu.parent.app.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 缴费项目详情表
 * </p>
 *
 * @author jing
 * @since 2022-05-24
 */
@Data
@ApiModel
public class PayDetailsBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目名称
     */
    @ApiModelProperty("项目名称")
    private String projectName;

    /**
     * 单价
     */
    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    /**
     * 数量
     */
    @ApiModelProperty("数量")
    private Integer number;

    /**
     * 金额
     */
    @ApiModelProperty("金额")
    private BigDecimal amount;

    /**
     * 项目说明
     */
    @ApiModelProperty("项目说明")
    private String projectExplain;

}
