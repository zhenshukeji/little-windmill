package com.zhenshu.system.business.kg.base.advanced.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 班级表 出参
 */
@Data
@ApiModel(description = "班级表 出参")
public class ClassroomBO implements Serializable {

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

    /**
     * 年级id
     */
    @ApiModelProperty("年级id")
    private Long gradeId;

    /**
     * 年级名
     */
    @ApiModelProperty(value = "年级名")
    private String gradeName;

    /**
     * 班主任的id
     */
    @ApiModelProperty(value = "班主任的id")
    private String teacherId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "班主任姓名")
    private String teacherName;

    /**
     * 副班主任的id
     */
    @ApiModelProperty(value = "副班主任的id")
    private String subTeacherId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "副班主任的姓名")
    private String subTeacherName;

}
