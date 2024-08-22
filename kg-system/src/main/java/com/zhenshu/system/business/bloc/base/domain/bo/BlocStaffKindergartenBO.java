package com.zhenshu.system.business.bloc.base.domain.bo;

import com.zhenshu.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-21
 * @desc 集团员工校区权限表出参
 */
@Data
@ApiModel(description = "集团员工校区权限表出参")
public class BlocStaffKindergartenBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学校id
     */
    @Excel(name = "学校id")
    @ApiModelProperty(value = "学校id")
    private Long kindergartenId;

    /**
     * 集团员工id
     */
    @Excel(name = "集团员工id")
    @ApiModelProperty(value = "集团员工id")
    private Long blocStaffId;

}
