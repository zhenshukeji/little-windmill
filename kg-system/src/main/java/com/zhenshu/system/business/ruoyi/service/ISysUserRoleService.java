package com.zhenshu.system.business.ruoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.common.enums.base.UserRoleType;
import com.zhenshu.system.business.ruoyi.domain.SysUserRole;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/15 13:37
 * @desc
 */
public interface ISysUserRoleService extends IService<SysUserRole> {
    /**
     * 通过用户ID删除用户和岗位关联
     *
     * @param uid 用户ID
     * @param type 用户类型
     */
    void deleteUserRoleByUserId(Long uid, UserRoleType type);

    /**
     * 批量新增用户岗位信息
     *
     * @param sysUserPosts 用户角色列表
     */
    void batchUserRole(List<SysUserRole> sysUserPosts);

    /**
     * 查询用户关联的岗位
     *
     * @param uid 登录用户id
     * @param type 用户类型
     * @return 结果
     */
    List<SysUserRole> selectUserJoinPost(Long uid, UserRoleType type);

    /**
     * 批量删除用户和岗位关联
     *
     * @param uids 用户集合ID
     * @param type
     */
    void deleteUserRoleByUserIds(List<Long> uids, UserRoleType type);
}
