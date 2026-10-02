package com.zhenshu.system.business.ruoyi.domain.bo;

import com.zhenshu.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/16 13:44
 * @desc 集团岗位出参
 */
@Data
@ApiModel(description = "集团岗位出参")
public class PostBO {
    /**
     * 岗位id
     */
    @Excel(name = "角色序号")
    @ApiModelProperty(value = "岗位id")
    private Long postId;

    /**
     * 岗位名称
     */
    @Excel(name = "岗位名称")
    @ApiModelProperty(value = "岗位名称")
    private String postName;

    /**
     * 角色状态（0正常 1停用）
     */
    @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    private String status;

    /**
     * 绑定员工数
     */
    @Excel(name = "绑定员工数")
    @ApiModelProperty(value = "绑定员工数")
    private Integer bindCount;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    @ApiModelProperty(value = "创建人")
    private String creator;
}
