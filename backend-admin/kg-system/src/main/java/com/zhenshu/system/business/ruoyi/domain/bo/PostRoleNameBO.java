package com.zhenshu.system.business.ruoyi.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-18
 * @desc 获取岗位名称出参
 */
@Data
@ApiModel(description = "获取岗位名称出参")
public class PostRoleNameBO {
    /**
     * 用户uid
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "岗位名称")
    private String postName;
}
