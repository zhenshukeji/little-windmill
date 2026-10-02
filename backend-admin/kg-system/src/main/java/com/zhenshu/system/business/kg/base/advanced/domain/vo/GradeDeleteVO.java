package com.zhenshu.system.business.kg.base.advanced.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-16
 * @desc 年级表 删除入参
 */
@Data
@ApiModel(description = "年级表 删除入参")
public class GradeDeleteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 年级id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "年级id")
    private Long id;
}
