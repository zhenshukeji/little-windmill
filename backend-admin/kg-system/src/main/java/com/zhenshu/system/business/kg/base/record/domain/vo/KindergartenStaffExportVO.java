package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/22 11:14
 * @desc 校区员工导出入参
 */
@Data
@ApiModel(description = "校区员工导出入参; true代表属性会被导出;")
public class KindergartenStaffExportVO {
    /**
     * 姓名
     */
    @NotNull
    @ApiModelProperty(required = true, value = "姓名")
    private Boolean name;

    /**
     * 岗位
     */
    @NotNull
    @ApiModelProperty(required = true, value = "岗位")
    private Boolean post;

    /**
     * 员工号码
     */
    @NotNull
    @ApiModelProperty(required = true, value = "员工号码")
    private Boolean staffNumber;

    /**
     * 婚姻状况
     */
    @NotNull
    @ApiModelProperty(required = true, value = "婚姻状况")
    private Boolean marriage;

    /**
     * 毕业院校
     */
    @NotNull
    @ApiModelProperty(required = true, value = "毕业院校")
    private Boolean school;

    /**
     * 民族
     */
    @NotNull
    @ApiModelProperty(required = true, value = "民族")
    private Boolean nation;

    /**
     * 现居住地址
     */
    @NotNull
    @ApiModelProperty(required = true, value = "现居住地址")
    private Boolean address;

    /**
     * 手机号码
     */
    @NotNull
    @ApiModelProperty(required = true, value = "手机号码")
    private Boolean phone;

    /**
     * 性别
     */
    @NotNull
    @ApiModelProperty(required = true, value = "性别")
    private Boolean sex;

    /**
     * 身份证号码
     */
    @NotNull
    @ApiModelProperty(required = true, value = "身份证号码")
    private Boolean identityNumber;

    /**
     * 入职日期
     */
    @NotNull
    @ApiModelProperty(required = true, value = "入职日期")
    private Boolean hiredate;

    /**
     * 学历
     */
    @NotNull
    @ApiModelProperty(required = true, value = "学历")
    private Boolean education;

    /**
     * 专业
     */
    @NotNull
    @ApiModelProperty(required = true, value = "专业")
    private Boolean major;

    /**
     * 户口所在地
     */
    @NotNull
    @ApiModelProperty(required = true, value = "户口所在地")
    private Boolean accountAddress;

    /**
     * 姓名
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "姓名")
    private String queryName;

    /**
     * 手机号码
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "手机号码")
    private String queryPhone;

    /**
     * 学校id
     */
    @ApiModelProperty(required = false, value = "学校id", hidden = true)
    private Long kgId;
}
