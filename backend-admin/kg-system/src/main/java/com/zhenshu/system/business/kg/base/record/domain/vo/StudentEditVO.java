package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.enums.system.UserSex;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 学生表 修改入参
 */
@Data
@ApiModel(description = "学生表 修改入参")
public class StudentEditVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "id")
    private Long id;

    /**
     * 学号
     */
    @Xss
    @Size(max = 128)
    @ApiModelProperty(required = false, value = "学号")
    private String studentNo;

    /**
     * 学生姓名
     */
    @Xss
    @Size(max = 50)
    @NotEmpty
    @ApiModelProperty(required = true, value = "学生姓名")
    private String name;

    /**
     * 性别 0女 1男
     */
    @NotNull
    @ApiModelProperty(required = true, value = "性别 0女 1男")
    private UserSex gender;

    /**
     * 民族
     */
    @Xss
    @Size(max = 32)
    @ApiModelProperty(required = false, value = "民族")
    private String nation;

    /**
     * 血型
     */
    @Xss
    @Size(max = 32)
    @ApiModelProperty(required = false, value = "血型")
    private String bloodType;

    /**
     * 出生日期 年月日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @ApiModelProperty(required = true, value = "出生日期 年月日")
    private LocalDate birthdate;

    /**
     * 健康状况
     */
    @Xss
    @Size(max = 32)
    @ApiModelProperty(required = false, value = "健康状况")
    private String healthStatus;

    /**
     * 证件类型
     */
    @Xss
    @Size(max = 32)
    @ApiModelProperty(required = false, value = "证件类型")
    private String cardType;

    /**
     * 证件号码
     */
    @Xss
    @Size(max = 30)
    @ApiModelProperty(required = false, value = "证件号码")
    private String cardNumber;

    /**
     * 国籍
     */
    @Xss
    @Size(max = 30)
    @ApiModelProperty(required = false, value = "国籍")
    private String nationality;

    /**
     * 班级ID
     */
    @NotNull
    @ApiModelProperty(required = true, value = "班级ID")
    private Long classId;

    /**
     * 就读方式
     */
    @Xss
    @Size(max = 32)
    @ApiModelProperty(required = false, value = "就读方式")
    private String studyingWay;

    /**
     * 入学日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(required = false, value = "入学日期")
    private LocalDate enrollDate;

    /**
     * 出生所在地
     */
    @Xss
    @Size(max = 100)
    @ApiModelProperty(required = false, value = "出生所在地")
    private String placeOfBirth;

    /**
     * 籍贯
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "籍贯")
    private String nativePlace;

    /**
     * 户口性质
     */
    @Xss
    @Size(max = 32)
    @ApiModelProperty(required = false, value = "户口性质")
    private String accountQuality;

    /**
     * 户口类型
     */
    @Xss
    @Size(max = 32)
    @ApiModelProperty(required = false, value = "户口类型")
    private String accountType;

    /**
     * 户口所在地
     */
    @Xss
    @Size(max = 100)
    @ApiModelProperty(required = false, value = "户口所在地")
    private String accountAddress;

    /**
     * 现住址
     */
    @Xss
    @Size(max = 200)
    @ApiModelProperty(required = false, value = "现住址")
    private String address;

    /**
     * 是否高位体弱
     */
    @ApiModelProperty(required = false, value = "是否高位体弱")
    private Boolean isWeak;

    /**
     * 是否独生子女
     */
    @ApiModelProperty(required = false, value = "是否独生子女")
    private Boolean isOnlyChild;

    /**
     * 是否留守学生
     */
    @ApiModelProperty(required = false, value = "是否留守学生")
    private Boolean isLeft;

    /**
     * 是否孤儿
     */
    @ApiModelProperty(required = false, value = "是否孤儿")
    private Boolean isOrphan;

    /**
     * 是否残疾儿童
     */
    @ApiModelProperty(required = false, value = "是否残疾儿童")
    private Boolean isDisability;

    /**
     * 是否务工子女
     */
    @ApiModelProperty(required = false, value = "是否务工子女")
    private Boolean isWorkers;

    /**
     * 特殊情况
     */
    @Xss
    @Size(max = 100)
    @ApiModelProperty(required = false, value = "特殊情况")
    private String specialCase;

    /**
     * 监护人
     */
    @Valid
    @Size(min = 1, max = 2)
    @ApiModelProperty(required = true, value = "监护人, 至少一个元素, 最多两个")
    private List<GuardianEditVO> guardians;

}
