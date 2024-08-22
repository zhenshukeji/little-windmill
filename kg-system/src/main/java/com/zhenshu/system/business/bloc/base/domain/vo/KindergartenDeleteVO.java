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
 * @desc 删除入参
 */
@Data
@ApiModel(description = "园区表 删除入参")
public class KindergartenDeleteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学校id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "学校id")
    private Long id;
}
