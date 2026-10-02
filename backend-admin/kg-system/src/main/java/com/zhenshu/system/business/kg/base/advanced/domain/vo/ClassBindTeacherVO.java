package com.zhenshu.system.business.kg.base.advanced.domain.vo;

import com.zhenshu.common.enums.kg.base.advanced.ClassBindTeacherType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/25 19:41
 * @desc 班级绑定老师入参
 */
@Data
@ApiModel(description = "班级绑定老师入参")
public class ClassBindTeacherVO {
    /**
     * 班级id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "班级id")
    private Long classId;

    /**
     * 老师id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "老师id")
    private Long teacherId;

    /**
     * 类型
     */
    @NotNull
    @ApiModelProperty(required = true, value = "类型")
    private ClassBindTeacherType type;
}
