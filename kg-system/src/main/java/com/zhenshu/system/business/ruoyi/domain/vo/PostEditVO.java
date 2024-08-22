package com.zhenshu.system.business.ruoyi.domain.vo;

import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/16 14:59
 * @desc 岗位修改入参
 */
@Data
@ApiModel(description = "岗位修改入参")
public class PostEditVO {
    /**
     * 岗位Id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "岗位Id")
    private Long postId;

    /**
     * 岗位名称
     */
    @Xss
    @Size(max = 30)
    @NotEmpty
    @ApiModelProperty(required = true, value = "岗位名称")
    private String postName;

    /**
     * 岗位能访问的菜单Id
     */
    @Size(min = 1)
    @NotNull
    @ApiModelProperty(required = true, value = "岗位能访问的菜单Id")
    private List<Long> menuList;
}
