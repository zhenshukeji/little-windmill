package com.zhenshu.system.business.ruoyi.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/15 15:40
 * @desc 岗位出参
 */
@Data
@ApiModel(description = "岗位出参")
public class RoleBO {
    /**
     * 岗位Id
     */
    @ApiModelProperty(value = "岗位Id")
    private Long roleId;

    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "岗位名称")
    private String roleName;
}
