package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.zhenshu.common.constant.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/16 11:23
 * @desc 校区重置密码入参
 */
@Data
@ApiModel(description = "校区重置密码入参")
public class KindergartenResetPasswordVO {
    /**
     * 校区Id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "校区Id")
    private Long id;

    /**
     * 新密码
     */
    @NotEmpty
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{" + Constants.PASSWORD_MIN_LENGTH + "," + Constants.PASSWORD_MAX_LENGTH + "}$")
    @ApiModelProperty(required = true, value = "新密码")
    private String password;
}
