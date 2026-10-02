package com.zhenshu.system.business.bloc.base.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.annotation.Excel;
import com.zhenshu.common.constant.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-11
 * @desc 详情出参
 */
@Data
@ApiModel(description = "集团员工表 详情出参")
public class BlocStaffDetailsBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录用户id
     */
    @ApiModelProperty(value = "登录用户id", hidden = true)
    private Long uid;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", hidden = true)
    private String avatar;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 集团Id; 为了查询后的判断;
     */
    @ApiModelProperty(value = "集团Id", hidden = true)
    private Long blocId;

    /**
     * 员工关联的岗位
     */
    @Size(min = Constants.ONE)
    @ApiModelProperty(required = true, value = "员工关联的岗位")
    private List<Long> postIds;

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
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String identityNumber;

    /**
     * 入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "入职日期")
    private Date hiredate;

    /**
     * 性别 0男 1女
     */
    @Size(min = 0, max = 1)
    @ApiModelProperty(value = "性别 0男 1女")
    private Integer sex;

    /**
     * 民族
     */
    @ApiModelProperty(value = "民族")
    private String nation;

    /**
     * 婚姻状况
     */
    @ApiModelProperty(value = "婚姻状况")
    private String marriage;

    /**
     * 现居住地址
     */
    @ApiModelProperty(value = "现居住地址")
    private String address;

    /**
     * 户口所在地
     */
    @ApiModelProperty(value = "户口所在地")
    private String accountAddress;

    /**
     * 学历
     */
    @Excel(name = "学历")
    @ApiModelProperty(value = "学历")
    private String education;

    /**
     * 毕业院校
     */
    @ApiModelProperty(value = "毕业院校")
    private String school;

    /**
     * 专业
     */
    @ApiModelProperty(value = "专业")
    private String major;

    /**
     * 说明
     */
    @ApiModelProperty(value = "说明")
    private String staffExplain;

    /**
     * 是否离职
     */
    @ApiModelProperty(value = "是否离职")
    private Boolean isQuit;

    /**
     * 离职日期
     */
    @ApiModelProperty(value = "离职日期")
    private Date quitDate;

    /**
     * 离职原因
     */
    @ApiModelProperty(value = "离职原因")
    private String quitReason;
}
