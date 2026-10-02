package com.zhenshu.system.business.kg.base.record.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zch
 * @version 1.0
 * @date 2022-02-15
 * @desc 学校教师出参
 */
@Data
@ApiModel(description = "学校教师出参")
public class KindergartenTeacherBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工id
     */
    @ApiModelProperty(value = "员工id")
    private Long id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
}
