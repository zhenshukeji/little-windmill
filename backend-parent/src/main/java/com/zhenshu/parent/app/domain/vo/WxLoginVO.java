package com.zhenshu.parent.app.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author jing
 * @version 1.0
 * @desc 微信登录入参
 * @date 2022/5/7 0007 16:17
 **/
@Data
@ApiModel
public class WxLoginVO {

    /**
     * 微信openid的授权code
     */
    @ApiModelProperty(name = "微信openid的授权code")
    @NotEmpty
    private String openidCode;

    /**
     * 手机号授权的code
     */
    @ApiModelProperty(name = "手机号授权的code")
    @NotEmpty
    private String phoneCode;

}
