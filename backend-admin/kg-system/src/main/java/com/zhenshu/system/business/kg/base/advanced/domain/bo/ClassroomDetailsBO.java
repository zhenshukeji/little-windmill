package com.zhenshu.system.business.kg.base.advanced.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 班级表 详情出参
 */
@Data
@ApiModel(description = "班级表 详情出参")
public class ClassroomDetailsBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级名
     */
    @ApiModelProperty(value = "班级名")
    private String className;

    /**
     * 年级id
     */
    @ApiModelProperty(value = "年级id")
    private Integer gradeId;

    /**
     * 班主任的uid
     */
    @ApiModelProperty(value = "班主任的uid")
    private String teacherId;

    /**
     * 副班主任的uid
     */
    @ApiModelProperty(value = "副班主任的uid")
    private String subTeacherId;

    /**
     * 校区Id
     */
    @ApiModelProperty(value = "校区Id")
    private Long kgId;
}
