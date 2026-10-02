package com.zhenshu.system.business.kg.base.record.domain.bo;

import com.zhenshu.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/4/28 10:10
 * @desc 学生导出出参
 */
@Data
@ApiModel(description = "学生导出出参")
public class StudentExportBO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 学生姓名
     */
    @Excel(name = "学生姓名", type = Excel.Type.EXPORT)
    private String name;

    /**
     * 年龄
     */
    @Excel(name = "年龄", type = Excel.Type.EXPORT)
    private String age;

    /**
     * 性别
     */
    @Excel(name = "性别", type = Excel.Type.EXPORT)
    private String gender;

    /**
     * 班级
     */
    @Excel(name = "班级", type = Excel.Type.EXPORT)
    private String className;

    /**
     * 教师
     */
    @Excel(name = "教师", type = Excel.Type.EXPORT)
    private String teacher;

    /**
     * 民族
     */
    @Excel(name = "民族", type = Excel.Type.EXPORT)
    private String nation;

    /**
     * 健康状况
     */
    @Excel(name = "健康状况", type = Excel.Type.EXPORT)
    private String healthStatus;

    /**
     * 证件号码
     */
    @Excel(name = "证件号码", type = Excel.Type.EXPORT)
    private String cardNumber;

    /**
     * 就读方式
     */
    @Excel(name = "就读方式", type = Excel.Type.EXPORT)
    private String studyingWay;

    /**
     * 出生日期 年月日
     */
    @Excel(name = "出生日期", type = Excel.Type.EXPORT)
    private LocalDate birthdate;

    /**
     * 籍贯
     */
    @Excel(name = "籍贯", type = Excel.Type.EXPORT)
    private String nativePlace;

    /**
     * 户口类型
     */
    @Excel(name = "户口类型", type = Excel.Type.EXPORT)
    private String accountType;

    /**
     * 现住址
     */
    @Excel(name = "现住址", type = Excel.Type.EXPORT)
    private String address;

    /**
     * 监护人1姓名
     */
    @Excel(name = "监护人1姓名", type = Excel.Type.EXPORT)
    private String guardianNameOne;

    /**
     * 监护人1关系
     */
    @Excel(name = "监护人1关系", type = Excel.Type.EXPORT)
    private String guardianRelationOne;

    /**
     * 监护人1联系电话
     */
    @Excel(name = "监护人1联系电话", type = Excel.Type.EXPORT)
    private String guardianPhoneOne;

    /**
     * 监护人1证件号码
     */
    @Excel(name = "监护人1证件号码", type = Excel.Type.EXPORT)
    private String guardianCardNumberOne;

    /**
     * 监护人1证件类型
     */
    @Excel(name = "监护人1证件类型", type = Excel.Type.EXPORT)
    private String guardianCardTypeOne;

    /**
     * 监护人1职业
     */
    @Excel(name = "监护人1职业", type = Excel.Type.EXPORT)
    private String guardianJobOne;

    /**
     * 监护人2姓名
     */
    @Excel(name = "监护人2姓名", type = Excel.Type.EXPORT)
    private String guardianNameTwo;

    /**
     * 监护人2联系电话
     */
    @Excel(name = "监护人2联系电话", type = Excel.Type.EXPORT)
    private String guardianPhoneTwo;

    /**
     * 监护人2关系
     */
    @Excel(name = "监护人2关系", type = Excel.Type.EXPORT)
    private String guardianRelationTwo;

    /**
     * 监护人2证件号码
     */
    @Excel(name = "监护人2证件号码", type = Excel.Type.EXPORT)
    private String guardianCardNumberTwo;

    /**
     * 监护人2证件类型
     */
    @Excel(name = "监护人2证件类型", type = Excel.Type.EXPORT)
    private String guardianCardTypeTwo;

    /**
     * 监护人2职业
     */
    @Excel(name = "监护人2职业", type = Excel.Type.EXPORT)
    private String guardianJobTwo;

    /**
     * 是否高位体弱
     */
    @Excel(name = "是否高位体弱", type = Excel.Type.EXPORT)
    private String isWeak;

    /**
     * 是否独生子女
     */
    @Excel(name = "是否独生子女", type = Excel.Type.EXPORT)
    private String isOnlyChild;

    /**
     * 是否留守学生
     */
    @Excel(name = "是否留守学生", type = Excel.Type.EXPORT)
    private String isLeft;

    /**
     * 是否孤儿
     */
    @Excel(name = "是否孤儿", type = Excel.Type.EXPORT)
    private String isOrphan;

    /**
     * 是否残疾儿童
     */
    @Excel(name = "是否残疾儿童", type = Excel.Type.EXPORT)
    private String isDisability;

    /**
     * 是否务工子女
     */
    @Excel(name = "是否务工子女", type = Excel.Type.EXPORT)
    private String isWorkers;

    /**
     * 血型
     */
    @Excel(name = "血型", type = Excel.Type.EXPORT)
    private String bloodType;

    /**
     * 证件类型
     */
    @Excel(name = "证件类型", type = Excel.Type.EXPORT)
    private String cardType;

    /**
     * 国籍
     */
    @Excel(name = "国籍", type = Excel.Type.EXPORT)
    private String nationality;

    /**
     * 入学日期
     */
    @Excel(name = "入学日期", type = Excel.Type.EXPORT)
    private String enrollDate;

    /**
     * 出生所在地
     */
    @Excel(name = "出生所在地", type = Excel.Type.EXPORT)
    private String placeOfBirth;

    /**
     * 户口性质
     */
    @Excel(name = "户口性质", type = Excel.Type.EXPORT)
    private String accountQuality;

    /**
     * 户口所在地
     */
    @Excel(name = "户口所在地", type = Excel.Type.EXPORT)
    private String accountAddress;

    /**
     * 特殊情况
     */
    @Excel(name = "特殊情况", type = Excel.Type.EXPORT)
    private String specialCase;

}
