package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;


/**
 * @author xxx
 * @version 1.0
 * @date 2022-07-04
 * @desc 查询幼儿园小程序用户表入参
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询幼儿园小程序用户表入参")
public class MiniLoginQueryVO extends PageEntity {
    /**
     * 微信openid
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "微信openid")
    private String openid;

    /**
     * 手机号
     */
    @Xss
    @Size(max = 255)
    @ApiModelProperty(required = false, value = "手机号")
    private String phone;

}
