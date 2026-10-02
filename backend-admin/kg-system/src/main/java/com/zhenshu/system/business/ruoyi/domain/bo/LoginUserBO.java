package com.zhenshu.system.business.ruoyi.domain.bo;

import com.zhenshu.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/17 17:02
 * @desc 登录用户出参
 */
@Data
@ApiModel(description = "登录用户出参")
public class LoginUserBO {
    /**
     * 登录用户Id
     */
    @Excel(name = "登录用户Id")
    @ApiModelProperty(value = "登录用户Id")
    private Long userId;

    /**
     * 用户名
     */
    @Excel(name = "用户名")
    @ApiModelProperty(value = "用户名")
    private String name;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 岗位名称
     */
    @Excel(name = "岗位名称")
    @ApiModelProperty(value = "岗位名称")
    private List<String> posts;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
    private String status;
}
