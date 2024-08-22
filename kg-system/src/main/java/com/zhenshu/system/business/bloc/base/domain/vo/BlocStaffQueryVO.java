package com.zhenshu.system.business.bloc.base.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-11
 * @desc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询集团员工表入参")
public class BlocStaffQueryVO extends PageEntity {
    /**
     * 姓名
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 离职
     */
    @NotNull
    @ApiModelProperty(required = true, value = "离职")
    private Boolean isQuit;
}
