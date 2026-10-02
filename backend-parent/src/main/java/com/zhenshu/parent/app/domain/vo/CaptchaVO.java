package com.zhenshu.parent.app.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author jing
 * @version 1.0
 * @desc 验证码入参
 * @date 2022/5/7 0007 16:27
 **/
@Data
@ApiModel
public class CaptchaVO {

    @NotEmpty
    @ApiModelProperty("手机号码")
    private String phoneNum;
}
