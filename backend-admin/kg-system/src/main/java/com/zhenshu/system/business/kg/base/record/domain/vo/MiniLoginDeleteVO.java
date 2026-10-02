package com.zhenshu.system.business.kg.base.record.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;


/**
 * @author xxx
 * @version 1.0
 * @date 2022-07-04
 * @desc 幼儿园小程序用户表删除入参
 */
@Data
@ApiModel(description = "幼儿园小程序用户表删除入参")
public class MiniLoginDeleteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "主键id")
    private Long id;
}
