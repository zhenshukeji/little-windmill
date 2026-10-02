package com.zhenshu.system.business.ruoyi.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/5/7 14:15
 * @desc 操作模块和菜单的返回出参
 */
@Data
@ApiModel(description = "出参")
public class LogOperateBO {
    /**
     * 菜单id
     */
    @ApiModelProperty(value = "菜单id")
    private Long id;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String name;
}
