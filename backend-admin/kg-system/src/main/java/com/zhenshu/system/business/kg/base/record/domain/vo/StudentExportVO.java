package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/22 11:14
 * @desc 学生导出入参
 */
@Data
@ApiModel(description = "学生导出入参; true代表属性会被导出;")
public class StudentExportVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 性别
     */
    @NotNull
    @ApiModelProperty(required = true, value = "性别")
    private Boolean gender;

    /**
     * 民族
     */
    @NotNull
    @ApiModelProperty(required = true, value = "民族")
    private Boolean nation;

    /**
     * 健康状况
     */
    @NotNull
    @ApiModelProperty(required = true, value = "健康状况")
    private Boolean healthStatus;

    /**
     * 证件号码
     */
    @NotNull
    @ApiModelProperty(required = true, value = "证件号码")
    private Boolean cardNumber;

    /**
     * 班级
     */
    @NotNull
    @ApiModelProperty(required = true, value = "班级")
    private Boolean className;

    /**
     * 就读方式
     */
    @NotNull
    @ApiModelProperty(required = true, value = "就读方式")
    private Boolean studyingWay;

    /**
     * 出生日期 年月日
     */
    @NotNull
    @ApiModelProperty(required = true, value = "出生日期")
    private Boolean birthdate;

    /**
     * 籍贯
     */
    @NotNull
    @ApiModelProperty(required = true, value = "籍贯")
    private Boolean nativePlace;

    /**
     * 户口类型
     */
    @NotNull
    @ApiModelProperty(required = true, value = "户口类型")
    private Boolean accountType;

    /**
     * 现住址
     */
    @NotNull
    @ApiModelProperty(required = true, value = "现住址")
    private Boolean address;

    /**
     * 监护人1关系
     */
    @NotNull
    @ApiModelProperty(required = true, value = "监护人1关系")
    private Boolean guardianRelationOne;

    /**
     * 监护人1职业
     */
    @NotNull
    @ApiModelProperty(required = true, value = "监护人1职业")
    private Boolean guardianJobOne;

    /**
     * 监护人1证件号码
     */
    @NotNull
    @ApiModelProperty(required = true, value = "监护人1证件号码")
    private Boolean guardianCardNumberOne;

    /**
     * 监护人1联系电话
     */
    @NotNull
    @ApiModelProperty(required = true, value = "监护人1联系电话")
    private Boolean guardianPhoneOne;

    /**
     * 监护人1证件类型
     */
    @NotNull
    @ApiModelProperty(required = true, value = "监护人1证件类型")
    private Boolean guardianCardTypeOne;

    /**
     * 监护人1姓名
     */
    @NotNull
    @ApiModelProperty(required = true, value = "监护人1姓名")
    private Boolean guardianNameOne;

    /**
     * 监护人2关系
     */
    @NotNull
    @ApiModelProperty(required = true, value = "监护人2关系")
    private Boolean guardianRelationTwo;

    /**
     * 监护人2职业
     */
    @NotNull
    @ApiModelProperty(required = true, value = "监护人2职业")
    private Boolean guardianJobTwo;

    /**
     * 监护人2证件号码
     */
    @NotNull
    @ApiModelProperty(required = true, value = "监护人2证件号码")
    private Boolean guardianCardNumberTwo;

    /**
     * 监护人2联系电话
     */
    @NotNull
    @ApiModelProperty(required = true, value = "监护人2联系电话")
    private Boolean guardianPhoneTwo;

    /**
     * 监护人2证件类型
     */
    @NotNull
    @ApiModelProperty(required = true, value = "监护人2证件类型")
    private Boolean guardianCardTypeTwo;

    /**
     * 监护人2姓名
     */
    @NotNull
    @ApiModelProperty(required = true, value = "监护人2姓名")
    private Boolean guardianNameTwo;

    /**
     * 是否高位体弱
     */
    @NotNull
    @ApiModelProperty(required = true, value = "是否高位体弱")
    private Boolean isWeak;

    /**
     * 是否独生子女
     */
    @NotNull
    @ApiModelProperty(required = true, value = "是否独生子女")
    private Boolean isOnlyChild;

    /**
     * 是否留守学生
     */
    @NotNull
    @ApiModelProperty(required = true, value = "是否留守学生")
    private Boolean isLeft;

    /**
     * 是否孤儿
     */
    @NotNull
    @ApiModelProperty(required = true, value = "是否孤儿")
    private Boolean isOrphan;

    /**
     * 是否残疾儿童
     */
    @NotNull
    @ApiModelProperty(required = true, value = "是否残疾儿童")
    private Boolean isDisability;

    /**
     * 是否务工子女
     */
    @NotNull
    @ApiModelProperty(required = true, value = "是否务工子女")
    private Boolean isWorkers;

    /**
     * 学生姓名
     */
    @NotNull
    @ApiModelProperty(required = true, value = "学生姓名")
    private Boolean name;

    /**
     * 年龄
     */
    @NotNull
    @ApiModelProperty(required = true, value = "年龄")
    private Boolean age;

    /**
     * 血型
     */
    @NotNull
    @ApiModelProperty(required = true, value = "血型")
    private Boolean bloodType;

    /**
     * 证件类型
     */
    @NotNull
    @ApiModelProperty(required = true, value = "证件类型")
    private Boolean cardType;

    /**
     * 国籍
     */
    @NotNull
    @ApiModelProperty(required = true, value = "国籍")
    private Boolean nationality;

    /**
     * 教师
     */
    @NotNull
    @ApiModelProperty(required = true, value = "教师")
    private Boolean teacher;

    /**
     * 入学日期
     */
    @NotNull
    @ApiModelProperty(required = true, value = "入学日期")
    private Boolean enrollDate;

    /**
     * 出生所在地
     */
    @NotNull
    @ApiModelProperty(required = true, value = "出生所在地")
    private Boolean placeOfBirth;

    /**
     * 户口性质
     */
    @NotNull
    @ApiModelProperty(required = true, value = "户口性质")
    private Boolean accountQuality;

    /**
     * 户口所在地
     */
    @NotNull
    @ApiModelProperty(required = true, value = "户口所在地")
    private Boolean accountAddress;

    /**
     * 特殊情况
     */
    @NotNull
    @ApiModelProperty(required = true, value = "特殊情况")
    private Boolean specialCase;

    /**
     * 信息
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "信息")
    private String info;

    /**
     * 班级ID
     */
    @ApiModelProperty(required = false, value = "班级ID;")
    private Integer classId;

    /**
     * 是否高位体弱
     */
    @ApiModelProperty(required = false, value = "是否高位体弱")
    private Boolean isWeakQuery;

    /**
     * 学校id
     */
    @ApiModelProperty(required = false, value = "学校id", hidden = true)
    private Long kgId;

}
