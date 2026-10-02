package com.zhenshu.system.business.ruoyi.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/16 19:07
 * @desc 岗位详情出参
 */
@Data
@ApiModel(description = "岗位详情出参")
public class PostDetailsBO {
    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "岗位名称")
    private String postName;

    /**
     * 岗位对应的菜单
     */
    @ApiModelProperty(value = "岗位对应的菜单")
    private List<MenuBO> menuList;

    /**
     * 已选中的菜单id
     */
    @ApiModelProperty(value = "已选中的菜单id")
    private Set<Long> checkedPosts;
}
