package com.zhenshu.system.business.ruoyi.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/18 19:25
 * @desc 登录用户Id入参
 */
@Data
@ApiModel(description = "登录用户Id入参")
public class UserIdVO {
    /**
     * 登录用户id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "登录用户id")
    private Long userId;
}
