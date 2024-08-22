package com.zhenshu.system.business.ruoyi.domain;

import com.zhenshu.common.enums.base.UserRoleType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户和角色关联 sys_user_role
 *
 * @author ruoyi
 */
public class SysUserRole
{
    /** 用户ID */
    private Long userId;

    /** 角色ID */
    private Long roleId;

    private UserRoleType userType;

    public UserRoleType getUserType() {
        return userType;
    }

    public void setUserType(UserRoleType userType) {
        this.userType = userType;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("roleId", getRoleId())
            .toString();
    }
}
