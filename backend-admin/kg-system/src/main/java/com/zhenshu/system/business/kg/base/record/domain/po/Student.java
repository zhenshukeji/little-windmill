package com.zhenshu.system.business.kg.base.record.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.common.domain.BasePO;
import com.zhenshu.common.enums.system.UserSex;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 学生表 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kg_student")
public class Student extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学号
     */
    private String studentNo;

    /**
     * 集团id
     */
    private Long blocId;

    /**
     * 学校id
     */
    private Long kgId;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 性别
     */
    private UserSex gender;

    /**
     * 学生头像url
     */
    private String imgUrl;

    /**
     * 学生真实头像
     */
    private String realImgUrl;

    /**
     * 民族
     */
    private String nation;

    /**
     * 血型
     */
    private String bloodType;

    /**
     * 出生日期 年月日
     */
    private LocalDate birthdate;

    /**
     * 健康状况
     */
    private String healthStatus;

    /**
     * 证件类型
     */
    private String cardType;

    /**
     * 证件号码
     */
    private String cardNumber;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 班级ID
     */
    private Long classId;

    /**
     * 就读方式
     */
    private String studyingWay;

    /**
     * 入学日期
     */
    private LocalDate enrollDate;

    /**
     * 出生所在地
     */
    private String placeOfBirth;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 户口性质
     */
    private String accountQuality;

    /**
     * 户口类型
     */
    private String accountType;

    /**
     * 户口所在地
     */
    private String accountAddress;

    /**
     * 现住址
     */
    private String address;

    /**
     * 是否高位体弱
     */
    private Boolean isWeak;

    /**
     * 是否独生子女
     */
    private Boolean isOnlyChild;

    /**
     * 是否留守学生
     */
    private Boolean isLeft;

    /**
     * 是否孤儿
     */
    private Boolean isOrphan;

    /**
     * 是否残疾儿童
     */
    private Boolean isDisability;

    /**
     * 是否务工子女
     */
    private Boolean isWorkers;

    /**
     * 特殊情况
     */
    private String specialCase;

    /**
     * 是否离校
     */
    private Boolean isLeaveSchool;

}
