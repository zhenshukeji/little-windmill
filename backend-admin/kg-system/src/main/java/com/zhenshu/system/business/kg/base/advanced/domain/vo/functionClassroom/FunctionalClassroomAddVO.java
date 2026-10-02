package com.zhenshu.system.business.kg.base.advanced.domain.vo.functionClassroom;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author xuzq
 * @Date 2022/9/28 19:34
 * @Version 1.0
 */
@Data
@ApiModel(description = "功能课室 新增入参")
public class FunctionalClassroomAddVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课室名称
     */
    @NotNull
    @ApiModelProperty(required = true, value = "课室名称")
    private String classroomName;

    /**
     * 场所类型 0户内 1户外
     */
    @NotNull
    @ApiModelProperty(required = true, value = "场所类型 0户内 1户外")
    private Integer placeType;

    /**
     * 是否允许多个班 false不允许 true 允许
     */
    @NotNull
    @ApiModelProperty(required = true, value = "是否允许多个班 false不允许 true 允许")
    private Boolean allowClass;

    /**
     * 是否允许不同年级 false不允许 true 允许
     */
    @NotNull
    @ApiModelProperty(required = true, value = "是否允许不同年级 false不允许 true 允许")
    private Boolean allowGrade;

    /**
     * 状态 false 禁用 true 启用
     */
    @NotNull
    @ApiModelProperty(required = true, value = "状态 false 禁用 true 启用")
    private Boolean enableFlag;

    /**
     * 功能课室描述
     */
    @NotNull
    @ApiModelProperty(required = true, value = "功能课室描述")
    private String classroomDesc;

    /**
     * 可预约班级数量
     */
    @NotNull
    @ApiModelProperty(required = true, value = "可预约班级数量")
    private Integer number;

}
