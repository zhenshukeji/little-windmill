package com.zhenshu.system.business.ruoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.system.business.ruoyi.domain.SysRoleMenu;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/17 17:35
 * @desc
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {
    /**
     * 通过角色ID删除角色和菜单关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteRoleMenuByRoleId(Long roleId);

    /**
     * 查询角色已选择的菜单
     *
     * @param roleId 角色Id
     * @return 结果
     */
    List<SysRoleMenu> getRolePickMenu(Long roleId);

    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    int batchRoleMenu(List<SysRoleMenu> roleMenuList);
}
