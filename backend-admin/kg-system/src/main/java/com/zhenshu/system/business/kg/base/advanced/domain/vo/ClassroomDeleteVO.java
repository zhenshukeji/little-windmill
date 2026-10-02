package com.zhenshu.system.business.kg.base.advanced.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 班级表 删除入参
 */
@Data
@ApiModel(description = "班级表 删除入参")
public class ClassroomDeleteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "班级id")
    private Long id;
}
