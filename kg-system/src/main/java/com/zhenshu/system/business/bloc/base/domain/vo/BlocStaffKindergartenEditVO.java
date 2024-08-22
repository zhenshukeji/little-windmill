package com.zhenshu.system.business.bloc.base.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-21
 * @desc 集团员工校区权限表修改入参
 */
@Data
@ApiModel(description = "集团员工校区权限表修改入参")
public class BlocStaffKindergartenEditVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 登录用户Id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "登录用户Id")
    private Long userId;

    /**
     * 校区Id集合
     */
    @ApiModelProperty(required = false, value = "校区Id集合")
    private Set<Long> kgIds;
}
