package com.zhenshu.parent.app.domain.bo.my;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/27 16:54
 * @desc 我的班级出参
 */
@Data
@ApiModel(description = "我的班级出参")
public class MyClassroomBO {
    /**
     * 班级id
     */
    @ApiModelProperty(value = "班级id")
    private Long id;

    /**
     * 班级名
     */
    @ApiModelProperty(value = "班级名")
    private String className;

    /**
     * 年级名
     */
    @ApiModelProperty(value = "年级名")
    private String gradeName;

    /**
     * 班主任名称
     */
    @ApiModelProperty(value = "班主任名称")
    private String teacherName;

    /**
     * 班主任手机号
     */
    @ApiModelProperty(value = "班主任手机号")
    private String teacherPhone;

    /**
     * 副班主任名称
     */
    @ApiModelProperty(value = "副班主任名称")
    private String subTeacherName;

    /**
     * 副班主任手机号
     */
    @ApiModelProperty(value = "副班主任手机号")
    private String subTeacherPhone;
}
