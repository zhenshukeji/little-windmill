package com.zhenshu.system.business.ruoyi.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/17 9:58
 * @desc 岗位删除入参
 */
@Data
@ApiModel(description = "岗位添加入参")
public class PostDeleteVO {
    /**
     * 岗位Id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "岗位Id")
    private Long postId;
}
