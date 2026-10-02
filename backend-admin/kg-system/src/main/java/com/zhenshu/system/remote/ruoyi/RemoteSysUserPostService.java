package com.zhenshu.system.remote.ruoyi;

import com.zhenshu.common.enums.base.UserRoleType;
import com.zhenshu.system.business.ruoyi.domain.SysUserRole;
import com.zhenshu.system.business.ruoyi.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/14 15:09
 * @desc
 */
@Service
public class RemoteSysUserPostService {
    @Resource
    private ISysUserRoleService sysUserRoleService;

    /**
     * 通过用户ID删除用户和岗位关联
     *
     * @param uid 用户ID
     * @param type 用户类型
     */
    public void deleteUserPostByUserId(Long uid, UserRoleType type) {
        sysUserRoleService.deleteUserRoleByUserId(uid, type);
    }

    /**
     * 批量删除用户和岗位关联
     *
     * @param uids 用户集合ID
     * @param type 用户类型
     */
    public void deleteUserPostByUserIds(List<Long> uids, UserRoleType type) {
        sysUserRoleService.deleteUserRoleByUserIds(uids, type);
    }

    /**
     * 批量新增用户岗位信息
     *
     * @param sysUserPosts 用户角色列表
     */
    public void batchUserPost(List<SysUserRole> sysUserPosts) {
        sysUserRoleService.batchUserRole(sysUserPosts);
    }

    /**
     * 查询用户关联的岗位
     *
     * @param uid 登录用户id
     * @param type 用户类型
     * @return 结果
     */
    public List<SysUserRole> selectUserJoinPost(Long uid, UserRoleType type) {
        return sysUserRoleService.selectUserJoinPost(uid, type);
    }
}
