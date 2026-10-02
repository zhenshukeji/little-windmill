package com.zhenshu.system.business.kg.base.advanced.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/23 17:33
 * @desc 简单的班级出参
 */
@Data
@ApiModel(description = "简单的班级出参")
public class ClassroomSimpleBO {
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

    /**
     * 班主任姓名
     */
    @ApiModelProperty(value = "班主任姓名")
    private String teacherName;

    /**
     * 学生数量
     */
    @ApiModelProperty("学生数量")
    private Integer studentCount;
}
