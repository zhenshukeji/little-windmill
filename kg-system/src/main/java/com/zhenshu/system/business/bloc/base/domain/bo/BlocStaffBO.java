package com.zhenshu.system.business.bloc.base.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-11
 * @desc
 */
@Data
@ApiModel(description = "集团员工表 出参")
public class BlocStaffBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 集团员工Id
     */
    @Excel(name = "集团员工Id")
    @ApiModelProperty(value = "集团员工Id")
    private Long id;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 性别 0男 1女 2未知
     */
    @Excel(name = "性别")
    @ApiModelProperty(value = "性别 0男 1女 2未知")
    private Integer sex;

    /**
     * 岗位
     */
    @Excel(name = "岗位")
    @ApiModelProperty(value = "岗位")
    private List<String> posts;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    @ApiModelProperty(value = "手机号码")
    private String phone;

    /**
     * 员工编号
     */
    @Excel(name = "员工编号")
    @ApiModelProperty(value = "员工编号")
    private String staffNumber;

    /**
     * 入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "入职日期")
    private Date hiredate;

    /**
     * 入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "离职日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "离职日期")
    private Date quitDate;

    /**
     * 学历
     */
    @Excel(name = "学历")
    @ApiModelProperty(value = "学历")
    private String education;

}
