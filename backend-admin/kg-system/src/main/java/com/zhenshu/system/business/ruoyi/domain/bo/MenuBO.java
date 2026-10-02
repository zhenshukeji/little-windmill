package com.zhenshu.system.business.ruoyi.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/18 18:40
 * @desc 菜单出参
 */
@Data
@ApiModel(description = "菜单出参")
public class MenuBO{
    /**
     * 菜单Id
     */
    @ApiModelProperty(value = "菜单Id")
    private Long menuId;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /**
     * 是否被选中
     */
    @ApiModelProperty(value = "是否被选中")
    private Boolean isChecked;

    /**
     * 菜单的后代元素
     */
    @ApiModelProperty(value = "菜单的后代元素")
    private List<MenuBO> childList;
}
