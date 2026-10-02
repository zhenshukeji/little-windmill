package com.zhenshu.system.business.kg.base.advanced.domain.bo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-15
 * @desc 班级可绑定老师出参
 */
@Data
@ApiModel(description = "班级可绑定老师出参")
public class TeacherBO {
    /**
     * 老师id
     */
    @ApiModelProperty(value = "老师id")
    private Long id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;
}
