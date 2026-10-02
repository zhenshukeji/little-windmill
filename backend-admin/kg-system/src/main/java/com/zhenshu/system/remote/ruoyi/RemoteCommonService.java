package com.zhenshu.system.remote.ruoyi;

import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.enums.base.RoleType;
import com.zhenshu.common.enums.base.UserRoleType;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.system.remote.ruoyi.RemoteSysPostService;
import com.zhenshu.system.remote.ruoyi.RemoteSysUserPostService;
import com.zhenshu.system.remote.ruoyi.RemoteSysUserService;
import com.zhenshu.system.business.ruoyi.domain.SysUserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/21 11:04
 * @desc 通用service
 */
@Slf4j
@Service
public class RemoteCommonService {
    @Resource
    private RemoteSysPostService remoteSysPostService;
    @Resource
    private RemoteSysUserService remoteSysUserService;
    @Resource
    private RemoteSysUserPostService remoteSysUserPostService;

    /**
     * 添加员工之前的校验; 校验岗位是否合法, 电话号码是否重复; 不合法会抛出异常
     *
     * @param type    校验的岗位类型
     * @param postIds 岗位id结婚
     * @param phone   电话号码
     */
    public void verifyPostAndPhone(RoleType type, List<Long> postIds, String phone) {
        // 1.检验岗位Id是否合法, 是否越权
        this.verifyPost(type, postIds);
        // 2.判断当前手机号是否已存在用户
        SysUser user = remoteSysUserService.selectByPhone(phone);
        if (user != null) {
            throw new ServiceException(ErrorEnums.PHONE_EXISTS);
        }
    }

    /**
     * 校验岗位是否合法;
     *
     * @param postIds 岗位ID
     * @return 结果
     */
    public void verifyPost(RoleType type, List<Long> postIds) {
        // 检验岗位Id是否合法, 是否越权
        boolean result = remoteSysPostService.verifyPost(type, postIds);
        if (!result) {
            throw new ServiceException(ErrorEnums.SYS_USER_NOT_EXIST);
        }
    }

    /**
     * 添加用户与岗位的关联关系
     *
     * @param uid     登录用户Id
     * @param postIds 岗位Id集合
     */
    public void addUserPost(Long uid, List<Long> postIds, UserRoleType userRoleType) {
        List<SysUserRole> sysUserRoles = new ArrayList<>(Constants.TEN);
        for (Long postId : postIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(uid);
            sysUserRole.setRoleId(postId);
            sysUserRole.setUserType(userRoleType);
            sysUserRoles.add(sysUserRole);
        }
        remoteSysUserPostService.batchUserPost(sysUserRoles);
    }
}
