package com.zhenshu.system.business.ruoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.enums.base.UserRoleType;
import com.zhenshu.system.business.ruoyi.domain.SysUserRole;
import com.zhenshu.system.business.ruoyi.mapper.SysUserRoleMapper;
import com.zhenshu.system.business.ruoyi.service.ISysRoleService;
import com.zhenshu.system.business.ruoyi.service.ISysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/15 13:38
 * @desc
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
    @Resource
    private ISysRoleService roleService;

    /**
     * 通过用户ID删除用户和岗位关联;
     *
     * @param uid  用户ID
     * @param type 用户类型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRoleByUserId(Long uid, UserRoleType type) {
        // 1.查询用户关联的岗位
        List<SysUserRole> sysUserRoles = this.selectUserJoinPost(uid, type);
        // 2.没有关联任何岗位结束方法
        if (CollectionUtils.isEmpty(sysUserRoles)) {
            return;
        }
        // 3.删除用户关联的岗位
        super.remove(
                new QueryWrapper<SysUserRole>().lambda()
                        .eq(SysUserRole::getUserId, uid)
                        .eq(SysUserRole::getUserType, type)
        );
        // 4.岗位绑定数量自减
        roleService.decrRoleBindCount(sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
    }

    /**
     * 批量新增用户岗位信息
     *
     * @param sysUserPosts 用户角色列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUserRole(List<SysUserRole> sysUserPosts) {
        // 1.批量保存用户与岗位的关联关系
        super.saveBatch(sysUserPosts);
        // 2.自增岗位的绑定数量
        roleService.incrRoleBindCount(sysUserPosts.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
    }

    /**
     * 查询用户关联的岗位
     *
     * @param uid  登录用户id
     * @param type 用户类型
     * @return 结果
     */
    @Override
    public List<SysUserRole> selectUserJoinPost(Long uid, UserRoleType type) {
        return super.list(
                new QueryWrapper<SysUserRole>().lambda()
                        .eq(SysUserRole::getUserId, uid)
                        .eq(SysUserRole::getUserType, type)
        );
    }

    /**
     * 批量删除用户和岗位关联
     *
     * @param uids 用户集合ID
     * @param type
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRoleByUserIds(List<Long> uids, UserRoleType type) {
        // 1.查询用户关联的岗位
        List<SysUserRole> sysUserRoles = this.list(
                new QueryWrapper<SysUserRole>().lambda()
                        .in(SysUserRole::getUserId, uids)
                        .eq(SysUserRole::getUserType, type)
        );
        // 2.没有关联任何岗位结束方法
        if (CollectionUtils.isEmpty(sysUserRoles)) {
            return;
        }
        // 3.删除用户关联的岗位
        super.remove(
                new QueryWrapper<SysUserRole>().lambda()
                        .in(SysUserRole::getUserId, uids)
                        .eq(SysUserRole::getUserType, type)
        );
        // 4.岗位绑定数量自减
        roleService.decrRoleBindCount(sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet()));
    }
}
