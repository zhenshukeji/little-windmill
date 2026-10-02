package com.zhenshu.system.business.bloc.base.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-11
 * @desc 集团员工Id入参
 */
@Data
@ApiModel(description = "集团员工Id入参")
public class BlocStaffIdVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 集团员工id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "集团员工id")
    private Long id;
}
