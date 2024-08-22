package com.zhenshu.system.business.ruoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.system.business.ruoyi.domain.SysRoleMenu;
import com.zhenshu.system.business.ruoyi.mapper.SysRoleMenuMapper;
import com.zhenshu.system.business.ruoyi.service.ISysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/17 17:35
 * @desc
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {
    /**
     * 通过角色ID删除角色和菜单关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int deleteRoleMenuByRoleId(Long roleId) {
        return baseMapper.deleteRoleMenuByRoleId(roleId);
    }

    /**
     * 查询角色已选择的菜单
     *
     * @param roleId 角色Id
     * @return 结果
     */
    @Override
    public List<SysRoleMenu> getRolePickMenu(Long roleId) {
        return baseMapper.selectList(
                new QueryWrapper<SysRoleMenu>().lambda()
                        .eq(SysRoleMenu::getRoleId, roleId)
        );
    }

    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    @Override
    public int batchRoleMenu(List<SysRoleMenu> roleMenuList) {
        return baseMapper.batchRoleMenu(roleMenuList);
    }
}
