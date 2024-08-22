package com.zhenshu.system.business.bloc.base.domain.bo;

import com.zhenshu.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-11
 * @desc
 */
@Data
@ApiModel(description = "园区表 出参")
public class KindergartenBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学校Id
     */
    @Excel(name = "学校Id")
    @ApiModelProperty(value = "学校Id")
    private Long id;

    /**
     * 学校名称
     */
    @Excel(name = "学校名称")
    @ApiModelProperty(value = "学校名称")
    private String kindergartenName;

    /**
     * 负责人姓名
     */
    @Excel(name = "负责人姓名")
    @ApiModelProperty(value = "负责人姓名")
    private String principalName;

    /**
     * 负责人联系电话
     */
    @Excel(name = "负责人联系电话")
    @ApiModelProperty(value = "负责人联系电话")
    private String principalPhone;

    /**
     * 学校地址
     */
    @Excel(name = "学校地址")
    @ApiModelProperty(value = "学校地址")
    private String kindergartenAddress;

    /**
     * 员工数
     */
    @Excel(name = "员工数")
    @ApiModelProperty(value = "员工数")
    private Integer staffCount;

    /**
     * 学生数
     */
    @Excel(name = "学生数")
    @ApiModelProperty(value = "学生数")
    private Integer studentCount;

    /**
     * 备注
     */
    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    @ApiModelProperty(value = "创建人")
    private String creator;

}
