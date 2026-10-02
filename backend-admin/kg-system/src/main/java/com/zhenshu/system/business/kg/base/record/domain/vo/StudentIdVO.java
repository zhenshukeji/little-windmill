package com.zhenshu.system.business.kg.base.record.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 学生Id入参
 */
@Data
@ApiModel(description = "学生Id入参")
public class StudentIdVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "id")
    private Long id;
}
