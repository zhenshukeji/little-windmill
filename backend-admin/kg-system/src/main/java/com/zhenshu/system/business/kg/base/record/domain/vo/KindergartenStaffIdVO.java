package com.zhenshu.system.business.kg.base.record.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-15
 * @desc 删除学校员工入参
 */
@Data
@ApiModel(description = "删除学校员工入参")
public class KindergartenStaffIdVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学校员工Id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "学校员工Id")
    private Long id;
}
