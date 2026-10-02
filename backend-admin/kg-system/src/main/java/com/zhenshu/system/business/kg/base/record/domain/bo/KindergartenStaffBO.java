package com.zhenshu.system.business.kg.base.record.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-15
 * @desc 学校员工表 出参
 */
@Data
@ApiModel(description = "学校员工表 出参")
public class KindergartenStaffBO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 员工id
     */
    @Excel(name = "员工id")
    @ApiModelProperty(value = "员工id")
    private Long id;

    /**
     * 员工编号
     */
    @Excel(name = "员工编号")
    @ApiModelProperty(value = "员工编号")
    private String staffNumber;

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
    @ApiModelProperty(value = "岗位; 逗号分隔")
    private String postNames;

    /**
     * 岗位id集合
     */
    @ApiModelProperty(value = "岗位id集合; 逗号分隔")
    private String postIds;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    @ApiModelProperty(value = "手机号码")
    private String phone;

    /**
     * 入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "入职日期")
    private Date hiredate;

    /**
     * 学历
     */
    @Excel(name = "学历")
    @ApiModelProperty(value = "学历")
    private String education;

    /**
     * 离职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "离职日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "离职日期")
    private Date quitDate;

}
