package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;


/**
 * @author xxx
 * @version 1.0
 * @date 2022-07-04
 * @desc 幼儿园小程序用户表修改入参
 */
@Data
@ApiModel(description = "幼儿园小程序用户表修改入参")
public class MiniLoginEditVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "主键id")
    private Long id;

    /**
     * 微信openid
     */
    @Xss
    @Size(max = 50)
    @NotEmpty
    @ApiModelProperty(required = true, value = "微信openid")
    private String openid;

    /**
     * 手机号
     */
    @Xss
    @Size(max = 255)
    @NotEmpty
    @ApiModelProperty(required = true, value = "手机号")
    private String phone;

}
