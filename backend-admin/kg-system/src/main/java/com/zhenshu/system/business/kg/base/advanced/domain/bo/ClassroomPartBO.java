package com.zhenshu.system.business.kg.base.advanced.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 只要部分数据
 */
@Data
@ApiModel(description = "班级表 出参")
public class ClassroomPartBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级id
     */
    @ApiModelProperty("班级id")
    private Long id;

    /**
     * 班级名
     */
    @ApiModelProperty(value = "班级名")
    private String className;

}
