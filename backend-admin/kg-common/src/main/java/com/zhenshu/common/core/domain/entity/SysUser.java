package com.zhenshu.common.core.domain.entity;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.*;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.enums.base.AssociationType;
import com.zhenshu.common.enums.base.BlocStaffStatus;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.enums.base.PlatformStatus;
import com.zhenshu.common.enums.system.UserSex;
import com.zhenshu.common.enums.system.UserStatus;
import com.zhenshu.common.utils.DateUtils;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.xss.Xss;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhenshu.common.annotation.Excel;
import com.zhenshu.common.annotation.Excel.ColumnType;
import com.zhenshu.common.annotation.Excel.Type;
import com.zhenshu.common.annotation.Excels;
import com.zhenshu.common.core.domain.BaseEntity;

/**
 * 用户对象 sys_user
 *
 * @author ruoyi
 */
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    @Excel(name = "用户序号", cellType = ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;

    /**
     * 部门ID
     */
    @Excel(name = "部门编号", type = Type.IMPORT)
    private Long deptId;

    /**
     * 用户账号
     */
    @Excel(name = "登录名称")
    private String userName;

    /**
     * 用户昵称
     */
    @Excel(name = "用户名称")
    private String nickName;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String phonenumber;

    /**
     * 用户性别
     */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private UserSex sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐加密
     */
    @TableField(exist = false)
    private String salt;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic(value = "0", delval = "2")
    private String delFlag;

    /**
     * 最后登录IP
     */
    @Excel(name = "最后登录IP", type = Type.EXPORT)
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date loginDate;

    /**
     * 部门对象
     */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    @TableField(exist = false)
    private SysDept dept;

    /**
     * 关联类型 0平台人员 1集团人员 2园区人员
     */
    private AssociationType associationType;

    /**
     * 关联id 0-platfrom_staff 平台人员表id 1-bloc_staff 集团人员表id 2-kindergarten 园区人员表id
     */
    private Long associationId;

    /**
     * 当前登录集团id
     */
    @TableField(exist = false)
    private Long blocId;

    /**
     * 当前登录园区id
     */
    @TableField(exist = false)
    private Long kgId;

    /**
     * 登录人员类型
     */
    @TableField(exist = false)
    private LoginIdentity identity;

    /**
     * 集团人员与园区关联的记录id
     */
    @TableField(exist = false)
    private Long blocStaffKindergartenId;

    /**
     * 集团人员登录状态
     */
    @TableField(exist = false)
    private BlocStaffStatus blocStaffStatus;

    /**
     * 平台人员登录状态
     */
    @TableField(exist = false)
    private PlatformStatus platformStatus;

    /**
     * 角色对象
     */
    @TableField(exist = false)
    private List<SysRole> roles;

    /**
     * 角色组
     */
    @TableField(exist = false)
    private Long[] roleIds;

    /**
     * 岗位组
     */
    @TableField(exist = false)
    private Long[] postIds;

    /**
     * 角色ID
     */
    @TableField(exist = false)
    private Long roleId;

    public SysUser() {

    }

    public Long getBlocStaffKindergartenId() {
        return blocStaffKindergartenId;
    }

    public void setBlocStaffKindergartenId(Long blocStaffKindergartenId) {
        this.blocStaffKindergartenId = blocStaffKindergartenId;
    }

    public PlatformStatus getPlatformStatus() {
        return platformStatus;
    }

    public void setPlatformStatus(PlatformStatus platformStatus) {
        this.platformStatus = platformStatus;
    }

    public BlocStaffStatus getBlocStaffStatus() {
        return blocStaffStatus;
    }

    public void setBlocStaffStatus(BlocStaffStatus blocStaffStatus) {
        this.blocStaffStatus = blocStaffStatus;
    }


    public LoginIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(LoginIdentity identity) {
        this.identity = identity;
    }

    public Long getBlocId() {
        return blocId;
    }

    public void setBlocId(Long blocId) {
        this.blocId = blocId;
    }

    public Long getKgId() {
        return kgId;
    }

    public void setKgId(Long kgId) {
        this.kgId = kgId;
    }

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Xss(message = "用户昵称不能包含脚本字符")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Xss(message = "用户账号不能包含脚本字符")
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public UserSex getSex() {
        return sex;
    }

    public void setSex(UserSex sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @JsonIgnore
    @JsonProperty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds() {
        return postIds;
    }

    public void setPostIds(Long[] postIds) {
        this.postIds = postIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public AssociationType getAssociationType() {
        return associationType;
    }

    public void setAssociationType(AssociationType associationType) {
        this.associationType = associationType;
    }

    public Long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Long associationId) {
        this.associationId = associationId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("userName", getUserName())
                .append("nickName", getNickName())
                .append("email", getEmail())
                .append("phonenumber", getPhonenumber())
                .append("sex", getSex())
                .append("avatar", getAvatar())
                .append("password", getPassword())
                .append("salt", getSalt())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("loginIp", getLoginIp())
                .append("loginDate", getLoginDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("dept", getDept())
                .append("associationId", getAssociationId())
                .append("identity", getIdentity())
                .append("blocId", getBlocId())
                .append("associationType", getAssociationType())
                .append("kindergartenId", getKgId())
                .append("blocStaffKindergartenId", getBlocStaffKindergartenId())
                .toString();
    }

    /**
     * 初始化登录用户对象; 自动填充(createBy createTime status delFlag)
     *
     * @return 结果
     */
    public static SysUser initSysUser(){
        SysUser sysUser = new SysUser();
        sysUser.setCreateBy(SecurityUtils.getUserId().toString());
        sysUser.setCreateTime(DateUtils.getNowDate());
        sysUser.setStatus(UserStatus.OK.getCode());
        sysUser.setDelFlag(Constants.RY_DEL_FLAG_EXIST);
        sysUser.setSex(UserSex.UNKNOWN);
        sysUser.setPassword(SecurityUtils.encryptPassword(Constants.DEFAULT_PASSWORD));
        return sysUser;
    }

}
