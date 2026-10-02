package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/8 10:01
 * @desc 学生缴费情况查询入参
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "学生缴费情况查询入参")
public class StudentPaymentQueryVO extends PageEntity {
    /**
     * 学生id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "学生id")
    private Long studentId;

    /**
     * 项目名称
     */
    @ApiModelProperty(required = false, value = "项目名称")
    private String projectName;

    @ApiModelProperty(hidden = true)
    private Long kgId;
}
