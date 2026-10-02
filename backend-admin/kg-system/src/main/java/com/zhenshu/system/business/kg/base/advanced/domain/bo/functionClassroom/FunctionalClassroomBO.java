package com.zhenshu.system.business.kg.base.advanced.domain.bo.functionClassroom;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuzq
 * @Date 2022/9/28 19:08
 * @Version 1.0
 */
@Data
@ApiModel(description = "功能课室出参")
public class FunctionalClassroomBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课室id
     */
    @ApiModelProperty(required = true, value = "课室id")
    private Long classroomId;

    /**
     * 课室名称
     */
    @ApiModelProperty(required = true, value = "课室名称")
    private String classroomName;

}
