package com.zhenshu.system.business.kg.base.record.domain.bo;

import com.zhenshu.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * @author xxx
 * @version 1.0
 * @date 2022-07-04
 * @desc 幼儿园小程序用户表出参
 */
@Data
@ApiModel(description = "幼儿园小程序用户表出参")
public class MiniLoginBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 微信openid
     */
    @Excel(name = "微信openid")
    @ApiModelProperty(value = "微信openid")
    private String openid;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    @ApiModelProperty(value = "手机号")
    private String phone;

}
