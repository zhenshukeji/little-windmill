package com.zhenshu.system.business.kg.base.advanced.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-03-01
 * @desc 学期表 删除入参
 */
@Data
@ApiModel(description = "学期表 删除入参")
public class SemesterDeleteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学期ID
     */
    @NotNull
    @ApiModelProperty(required = true, value = "学期ID")
    private Long id;
}
