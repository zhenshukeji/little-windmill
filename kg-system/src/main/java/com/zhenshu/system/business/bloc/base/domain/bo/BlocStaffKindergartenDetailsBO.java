package com.zhenshu.system.business.bloc.base.domain.bo;

import com.zhenshu.system.business.bloc.tokg.domain.bo.GoToKindergartenBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-21
 * @desc 集团员工校区权限表详情出参
 */
@Data
@ApiModel(description = "集团员工校区权限表详情出参")
public class BlocStaffKindergartenDetailsBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 集团员工姓名
     */
    @ApiModelProperty(value = "集团员工姓名")
    private String blocStaffName;

    /**
     * 登录用户能操作的所有校区集合
     */
    @ApiModelProperty(value = "登录用户能操作的所有校区集合")
    private List<GoToKindergartenBO> kgList;
}
