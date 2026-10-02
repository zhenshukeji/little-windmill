package com.zhenshu.system.business.kg.base.record.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.annotation.Excel;
import com.zhenshu.common.enums.system.UserSex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 学生表 出参
 */
@Data
@ApiModel(description = "学生表 出参")
public class StudentBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生Id
     */
    @Excel(name = "学生Id")
    @ApiModelProperty(value = "学生Id")
    private Long id;

    /**
     * 班级
     */
    @Excel(name = "班级")
    @ApiModelProperty(value = "班级")
    private String className;

    /**
     * 学生姓名
     */
    @Excel(name = "学生姓名")
    @ApiModelProperty(value = "学生姓名")
    private String name;

    /**
     * 性别
     */
    @Excel(name = "性别")
    @ApiModelProperty(value = "性别")
    private UserSex gender;

    /**
     * 监护人姓名
     */
    @Excel(name = "监护人姓名")
    @ApiModelProperty(value = "监护人姓名, 使用逗号分隔")
    private String guardianName;

    /**
     * 监护人联系电话
     */
    @Excel(name = "监护人联系电话")
    @ApiModelProperty(value = "监护人联系电话, 使用逗号分隔")
    private String guardianPhone;

    /**
     * 是否高危体弱
     */
    @Excel(name = "是否高危体弱")
    @ApiModelProperty(value = "是否高危体弱")
    private Boolean isWeak;

    /**
     * 特殊情况
     */
    @Excel(name = "特殊情况")
    @ApiModelProperty(value = "特殊情况")
    private String specialCase;

    /**
     * 图片url
     */
    @ApiModelProperty(value = "图片url")
    private String realImgUrl;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;



    /**
     * 入学日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入学日期")
    private LocalDate enrollDate;

    /**
     * 民族
     */
    @ApiModelProperty(value = "民族")
    private String nation;

    /**
     * 血型
     */
    @ApiModelProperty(value = "血型")
    private String bloodType;

    /**
     * 国籍
     */
    @ApiModelProperty(value = "国籍")
    private String nationality;

    /**
     * 是否残疾儿童
     */
    @ApiModelProperty(value = "是否残疾儿童")
    private Boolean isDisability;

    /**
     * 健康状况
     */
    @ApiModelProperty(value = "健康状况")
    private String healthStatus;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private String age;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期", hidden = true)
    private LocalDate birthdate;

}
