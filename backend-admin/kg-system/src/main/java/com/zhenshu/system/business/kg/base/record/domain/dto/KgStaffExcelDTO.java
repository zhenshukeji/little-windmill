package com.zhenshu.system.business.kg.base.record.domain.dto;

import com.zhenshu.common.annotation.Excel;
import com.zhenshu.common.xss.Xss;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/22 16:01
 * @desc 校区人员导入excel对象
 */
@Data
public class KgStaffExcelDTO {
    /**
     * 姓名
     */
    @Xss
    @Size(max = 50)
    @NotEmpty
    @Excel(name = "姓名", type = Excel.Type.IMPORT)
    private String name;

    /**
     * 手机号码
     */
    @Xss
    @Size(max = 50)
    @NotEmpty
    @Excel(name = "手机号码", type = Excel.Type.IMPORT)
    private String phone;

    /**
     * 岗位
     */
    @Xss
    @NotEmpty
    @Excel(name = "岗位", type = Excel.Type.IMPORT)
    private String post;

    /**
     * 性别
     */
    @Pattern(regexp = "^[0-1]$")
    @Excel(name = "性别", type = Excel.Type.IMPORT, readConverterExp = "0=男,1=女")
    private String sex;

    /**
     * 身份证号码
     */
    @Xss
    @Size(max = 100)
    @Excel(name = "身份证号码", type = Excel.Type.IMPORT)
    private String identityNumber;

    /**
     * 员工编号
     */
    @Xss
    @Size(max = 50)
    @Excel(name = "员工编号", type = Excel.Type.IMPORT)
    private String staffNumber;

    /**
     * 入职日期
     */
    @Excel(name = "入职日期", dateFormat = "yyyy-MM-dd", type = Excel.Type.IMPORT)
    private Date hiredate;

    /**
     * 婚姻状况
     */
    @Xss
    @Size(max = 32)
    @Excel(name = "婚姻状况", type = Excel.Type.IMPORT)
    private String marriage;

    /**
     * 学历
     */
    @Xss
    @Size(max = 32)
    @Excel(name = "学历", type = Excel.Type.IMPORT)
    private String education;

    /**
     * 毕业院校
     */
    @Xss
    @Size(max = 32)
    @Excel(name = "毕业院校", type = Excel.Type.IMPORT)
    private String school;

    /**
     * 专业
     */
    @Xss
    @Size(max = 32)
    @Excel(name = "专业", type = Excel.Type.IMPORT)
    private String major;

    /**
     * 民族
     */
    @Xss
    @Size(max = 32)
    @Excel(name = "民族", type = Excel.Type.IMPORT)
    private String nation;

    /**
     * 户口所在地
     */
    @Xss
    @Size(max = 100)
    @Excel(name = "户口所在地", type = Excel.Type.IMPORT)
    private String accountAddress;

    /**
     * 现居住地址
     */
    @Xss
    @Size(max = 100)
    @Excel(name = "现居住地址", type = Excel.Type.IMPORT)
    private String address;
}
