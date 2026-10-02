package com.zhenshu.parent.app.domain.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author jing
 * @since 2022-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("kg_student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 班级ID
     */
    private Long classId;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 性别 0女 1男
     */
    private Integer gender;

    /**
     * 学生微信openid
     */
    private String openid;

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
     * 是否高危体弱
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

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除 0false 1true
     */
    @TableLogic
    private Boolean delFlag;


}
