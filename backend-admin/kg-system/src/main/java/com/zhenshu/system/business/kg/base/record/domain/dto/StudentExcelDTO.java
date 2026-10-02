package com.zhenshu.system.business.kg.base.record.domain.dto;

import com.zhenshu.common.annotation.Excel;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/22 11:14
 * @desc 学生导出入参
 */
@Data
@ApiModel(description = "学生导出入参; true代表属性会被导出;")
public class StudentExcelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生姓名
     */
    @Xss
    @NotEmpty
    @Size(max = 50)
    @Excel(name = "姓名", type = Excel.Type.IMPORT)
    private String name;

    /**
     * 性别
     */
    @NotNull
    @Range(max = 1)
    @Excel(name = "性别", type = Excel.Type.IMPORT, readConverterExp = "0=男,1=女")
    private Integer gender;

    /**
     * 出生日期
     */
    @NotNull
    @Excel(name = "出生日期", dateFormat = "yyyy-MM-dd", type = Excel.Type.IMPORT)
    private Date birthdate;

    /**
     * 班级
     */
    @Xss
    @NotNull
    @Size(max = 50)
    @Excel(name = "班级", type = Excel.Type.IMPORT)
    private String className;

    /**
     * 证件类型
     */
    @Xss
    @Size(max = 30)
    @Excel(name = "证件类型", type = Excel.Type.IMPORT)
    private String cardType;

    /**
     * 证件号码
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "证件号码", type = Excel.Type.IMPORT)
    private String cardNumber;

    /**
     * 是否高危
     */
    @Excel(name = "是否高危", type = Excel.Type.IMPORT, readConverterExp = "1=是,0=否")
    private Integer isWeak;

    /**
     * 血型
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "血型", type = Excel.Type.IMPORT)
    private String bloodType;

    /**
     * 国籍/地区
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "国籍/地区", type = Excel.Type.IMPORT)
    private String nationality;

    /**
     * 民族
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "民族", type = Excel.Type.IMPORT)
    private String nation;

    /**
     * 出生所在地
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "出生所在地", type = Excel.Type.IMPORT)
    private String placeOfBirth;

    /**
     * 籍贯
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "籍贯", type = Excel.Type.IMPORT)
    private String nativePlace;

    /**
     * 户口性质
     */
    @Xss
    @Size(max = 30)
    @Excel(name = "户口性质", type = Excel.Type.IMPORT)
    private String accountQuality;

    /**
     * 户口类型
     */
    @Xss
    @Size(max = 30)
    @Excel(name = "户口类型", type = Excel.Type.IMPORT)
    private String accountType;

    /**
     * 户口所在地
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "户口所在地", type = Excel.Type.IMPORT)
    private String accountAddress;

    /**
     * 现住址
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "现住址", type = Excel.Type.IMPORT)
    private String address;

    /**
     * 入学日期
     */
    @Excel(name = "入学日期", type = Excel.Type.IMPORT, dateFormat = "yyyy-MM-dd")
    private Date enrollDate;

    /**
     * 就读方式
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "就读方式", type = Excel.Type.IMPORT)
    private String studyingWay;

    /**
     * 是否独生子女
     */
    @Excel(name = "是否独生子女", type = Excel.Type.IMPORT, readConverterExp = "1=是,0=否")
    private Integer isOnlyChild;

    /**
     * 是否留守学生
     */
    @Excel(name = "是否留守学生", type = Excel.Type.IMPORT, readConverterExp = "1=是,0=否")
    private Integer isLeft;

    /**
     * 是否进城务工人员子女
     */
    @Excel(name = "是否进城务工人员子女", type = Excel.Type.IMPORT, readConverterExp = "1=是,0=否")
    private Integer isWorkers;

    /**
     * 是否残疾幼儿
     */
    @Excel(name = "是否残疾幼儿", type = Excel.Type.IMPORT, readConverterExp = "1=是,0=否")
    private Integer isDisability;

    /**
     * 是否孤儿
     */
    @Excel(name = "是否孤儿", type = Excel.Type.IMPORT, readConverterExp = "1=是,0=否")
    private Integer isOrphan;

    /**
     * 健康状况
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "健康状况", type = Excel.Type.IMPORT)
    private String healthStatus;

    /**
     * 监护人1姓名
     */
    @Xss
    @NotEmpty
    @Size(max = 50)
    @Excel(name = "监护人1姓名", type = Excel.Type.IMPORT)
    private String guardianNameOne;

    /**
     * 监护人1身份证件类型
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "监护人1身份证件类型", type = Excel.Type.IMPORT)
    private String guardianCardTypeOne;

    /**
     * 监护人1身份证件号码
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "监护人1身份证件号码", type = Excel.Type.IMPORT)
    private String guardianCardNumberOne;

    /**
     * 监护人1手机号码
     */
    @Xss
    @NotEmpty
    @Size(max = 30)
    @Excel(name = "监护人1手机号码", type = Excel.Type.IMPORT)
    private String guardianPhoneOne;

    /**
     * 监护人2姓名
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "监护人2姓名", type = Excel.Type.IMPORT)
    private String guardianNameTwo;

    /**
     * 监护人2身份证件类型
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "监护人2身份证件类型", type = Excel.Type.IMPORT)
    private String guardianCardTypeTwo;

    /**
     * 监护人2身份证件号码
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "监护人2身份证件号码", type = Excel.Type.IMPORT)
    private String guardianCardNumberTwo;

    /**
     * 监护人2手机号码
     */
    @Xss
    @Size(max = 30)
    @Excel(name = "监护人2手机号码", type = Excel.Type.IMPORT)
    private String guardianPhoneTwo;
}
