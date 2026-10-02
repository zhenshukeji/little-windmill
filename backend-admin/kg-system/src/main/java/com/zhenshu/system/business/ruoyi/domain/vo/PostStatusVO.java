package com.zhenshu.system.business.ruoyi.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/17 14:24
 * @desc 编辑岗位状态入参
 */
@Data
@ApiModel(description = "编辑岗位状态入参")
public class PostStatusVO {
    /**
     * 岗位Id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "岗位Id")
    private Long postId;

    /**
     * 岗位状态 0:正常 1:停用
     */
    @NotEmpty
    @Pattern(regexp = "^[0,1]$")
    @ApiModelProperty(required = true, value = "岗位状态 0:正常 1:停用")
    private String status;
}
