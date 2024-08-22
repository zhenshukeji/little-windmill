package com.zhenshu.system.business.bloc.base.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-11
 * @desc 新增入参
 */
@Data
@ApiModel(description = "集团员工表 新增入参")
public class BlocStaffAddVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @Xss
    @Size(max = 50)
    @NotEmpty
    @ApiModelProperty(required = true, value = "姓名")
    private String name;

    /**
     * 员工关联的岗位
     */
    @NotNull
    @Size(min = Constants.ONE)
    @ApiModelProperty(required = true, value = "员工关联的岗位")
    private List<Long> postIds;

    /**
     * 手机号码
     */
    @Xss
    @Size(max = 50)
    @NotEmpty
    @ApiModelProperty(required = true, value = "手机号码")
    private String phone;

    /**
     * 员工照片
     */
    @Xss
    @Size(max = 100)
    @ApiModelProperty(value = "员工照片")
    private String avatar;

    /**
     * 员工编号
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(value = "员工编号")
    private String staffNumber;

    /**
     * 身份证号码
     */
    @Xss
    @Size(max = 100)
    @ApiModelProperty(value = "身份证号码")
    private String identityNumber;

    /**
     * 入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入职日期")
    private Date hiredate;

    /**
     * 性别 0男 1女
     */
    @NotNull
    @Range(min = 0, max = 1)
    @ApiModelProperty(required = true, value = "性别 0男 1女")
    private Integer sex;

    /**
     * 民族
     */
    @Xss
    @Size(max = 32)
    @ApiModelProperty(value = "民族")
    private String nation;

    /**
     * 婚姻状况
     */
    @Xss
    @Size(max = 32)
    @ApiModelProperty(value = "婚姻状况")
    private String marriage;

    /**
     * 现居住地址
     */
    @Xss
    @Size(max = 100)
    @ApiModelProperty(value = "现居住地址")
    private String address;

    /**
     * 户口所在地
     */
    @Xss
    @Size(max = 100)
    @ApiModelProperty(value = "户口所在地")
    private String accountAddress;

    /**
     * 学历
     */
    @Xss
    @Size(max = 32)
    @ApiModelProperty(value = "学历")
    private String education;

    /**
     * 毕业院校
     */
    @Xss
    @Size(max = 32)
    @ApiModelProperty(value = "毕业院校")
    private String school;

    /**
     * 专业
     */
    @Xss
    @Size(max = 32)
    @ApiModelProperty(value = "专业")
    private String major;

    /**
     * 说明
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(value = "说明")
    private String staffExplain;

}
