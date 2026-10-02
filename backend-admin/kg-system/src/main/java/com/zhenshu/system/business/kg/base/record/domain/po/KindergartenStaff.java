package com.zhenshu.system.business.kg.base.record.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.common.domain.BasePO;
import com.zhenshu.common.enums.base.UserIdentity;
import com.zhenshu.common.enums.system.UserSex;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zch
 * @version 1.0
 * @date 2022-02-15
 * @desc 学校员工表 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kg_kindergarten_staff")
public class KindergartenStaff extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 集团id
     */
    private Long blocId;

    /**
     * 学校id
     */
    private Long kgId;

    /**
     * 登录用户id
     */
    private Long uid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private UserSex sex;

    /**
     * 身份 0-管理员 1-普通人员
     */
    private UserIdentity identity;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 员工编号
     */
    private String staffNumber;

    /**
     * 身份证号码
     */
    private String identityNumber;

    /**
     * 入职日期
     */
    private Date hiredate;

    /**
     * 民族
     */
    private String nation;

    /**
     * 婚姻状况
     */
    private String marriage;

    /**
     * 现居住地址
     */
    private String address;

    /**
     * 户口所在地
     */
    private String accountAddress;

    /**
     * 学历
     */
    private String education;

    /**
     * 毕业院校
     */
    private String school;

    /**
     * 专业
     */
    private String major;

    /**
     * 说明
     */
    private String staffExplain;

    /**
     * 是否离职
     */
    private Boolean isQuit;

    /**
     * 离职日期
     */
    private Date quitDate;

    /**
     * 离职原因
     */
    private String quitReason;

}
