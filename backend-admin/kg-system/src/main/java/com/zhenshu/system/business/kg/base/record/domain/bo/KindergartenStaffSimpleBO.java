package com.zhenshu.system.business.kg.base.record.domain.bo;

import com.zhenshu.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-28
 * @desc 简单的学校员工出参
 */
@Data
@ApiModel(description = "简单的学校员工出参")
public class KindergartenStaffSimpleBO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 员工id
     */
    @Excel(name = "员工id")
    @ApiModelProperty(value = "员工id")
    private Long id;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String name;

}
