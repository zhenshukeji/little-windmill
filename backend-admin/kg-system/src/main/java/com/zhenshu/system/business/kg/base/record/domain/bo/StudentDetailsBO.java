package com.zhenshu.system.business.kg.base.record.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.enums.system.UserSex;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 学生表 详情出参
 */
@Data
@ApiModel(description = "学生表 详情出参")
public class StudentDetailsBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 学号
     */
    @ApiModelProperty(value = "学号")
    private String studentNo;

    /**
     * 学生姓名
     */
    @ApiModelProperty(value = "学生姓名")
    private String name;

    /**
     * 性别 0男 1女
     */
    @ApiModelProperty(value = "性别 0男 1女")
    private UserSex gender;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private String age;

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
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private LocalDate birthdate;

    /**
     * 健康状况
     */
    @ApiModelProperty(value = "健康状况")
    private String healthStatus;

    /**
     * 证件类型
     */
    @ApiModelProperty(value = "证件类型")
    private String cardType;

    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    private String cardNumber;

    /**
     * 国籍
     */
    @ApiModelProperty(value = "国籍")
    private String nationality;

    /**
     * 班级ID
     */
    @ApiModelProperty(value = "班级ID")
    private Long classId;

    /**
     * 就读方式
     */
    @ApiModelProperty(value = "就读方式")
    private String studyingWay;

    /**
     * 入学日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入学日期")
    private LocalDate enrollDate;

    /**
     * 出生所在地
     */
    @ApiModelProperty(value = "出生所在地")
    private String placeOfBirth;

    /**
     * 籍贯
     */
    @ApiModelProperty(value = "籍贯")
    private String nativePlace;

    /**
     * 户口性质
     */
    @ApiModelProperty(value = "户口性质")
    private String accountQuality;

    /**
     * 户口类型
     */
    @ApiModelProperty(value = "户口类型")
    private String accountType;

    /**
     * 户口所在地
     */
    @ApiModelProperty(value = "户口所在地")
    private String accountAddress;

    /**
     * 现住址
     */
    @ApiModelProperty(value = "现住址")
    private String address;

    /**
     * 是否高位体弱
     */
    @ApiModelProperty(value = "是否高位体弱")
    private Boolean isWeak;

    /**
     * 是否独生子女
     */
    @ApiModelProperty(value = "是否独生子女")
    private Boolean isOnlyChild;

    /**
     * 是否留守学生
     */
    @ApiModelProperty(value = "是否留守学生")
    private Boolean isLeft;

    /**
     * 是否孤儿
     */
    @ApiModelProperty(value = "是否孤儿")
    private Boolean isOrphan;

    /**
     * 是否残疾儿童
     */
    @ApiModelProperty(value = "是否残疾儿童")
    private Boolean isDisability;

    /**
     * 是否务工子女
     */
    @ApiModelProperty(value = "是否务工子女")
    private Boolean isWorkers;

    /**
     * 特殊情况
     */
    @ApiModelProperty(value = "特殊情况")
    private String specialCase;

    /**
     * 监护人
     */
    @ApiModelProperty(value = "监护人")
    private List<GuardianBO> guardians;

    /**
     * 班级名称
     */
    @ApiModelProperty(value = "班级名称")
    private String className;

    /**
     * 老师名称
     */
    @ApiModelProperty(value = "老师名称")
    private String teacherName;
}
