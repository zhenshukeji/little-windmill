package com.zhenshu.system.business.kg.work.educational.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-09
 * @desc 教学计划表 删除入参
 */
@Data
@ApiModel(description = "教学计划 删除入参")
public class TeachingProgramDeleteVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 教学计划id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "教学计划id")
    private Long id;
}
