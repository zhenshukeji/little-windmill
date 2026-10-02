package com.zhenshu.system.business.ruoyi.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/17 16:58
 * @desc 登录人员查询入参
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "登录人员查询入参")
public class LoginUserQueryVO extends PageEntity {
    /**
     * 查询参数 员工姓名或手机号
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(value = "查询参数 员工姓名或手机号")
    private String param;
}
