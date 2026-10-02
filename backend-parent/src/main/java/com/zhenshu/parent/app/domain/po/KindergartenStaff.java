package com.zhenshu.parent.app.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.parent.common.constant.enums.UserIdentity;
import com.zhenshu.parent.common.constant.enums.UserSex;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 园区员工表
 * </p>
 *
 * @author zch
 * @since 2022-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("kg_kindergarten_staff")
public class KindergartenStaff implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课件id
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
