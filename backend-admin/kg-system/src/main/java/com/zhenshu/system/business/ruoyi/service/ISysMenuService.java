package com.zhenshu.system.business.ruoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.common.core.domain.TreeSelect;
import com.zhenshu.common.core.domain.entity.SysMenu;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.enums.base.MenuCategory;
import com.zhenshu.system.business.ruoyi.domain.bo.LogOperateBO;
import com.zhenshu.system.business.ruoyi.domain.bo.MenuBO;
import com.zhenshu.system.business.ruoyi.domain.vo.RouterVo;

import java.util.List;
import java.util.Set;

/**
 * 菜单 业务层
 *
 * @author ruoyi
 */
public interface ISysMenuService extends IService<SysMenu> {
    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(Long userId);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu   菜单信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    public List<Long> selectMenuListByRoleId(Long roleId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    public List<RouterVo> buildMenus(List<SysMenu> menus);

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    public List<SysMenu> buildMenuTree(List<SysMenu> menus);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    public SysMenu selectMenuById(Long menuId);

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean hasChildByMenuId(Long menuId);

    /**
     * 查询菜单是否存在角色
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkMenuExistRole(Long menuId);

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int insertMenu(SysMenu menu);

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int updateMenu(SysMenu menu);

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    public int deleteMenuById(Long menuId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public String checkMenuNameUnique(SysMenu menu);

    /**
     * 根据菜单类别查询权限
     *
     * @param categoryArr 菜单数组
     * @return 列表
     */
    Set<String> selectPermsByCategory(int[] categoryArr);

    /**
     * 根据用户id和对应的身份，查询在某个范围内的权限
     *
     * @param userId   用户id
     * @param userType 用户类型
     * @return 结果
     */
    Set<String> selectPermsByUserIdAndType(Long userId, Integer userType);

    /**
     * 查询路由列表
     *
     * @param user 用户信息
     * @return 路由
     */
    List<SysMenu> selectMenuTreeByUser(SysUser user);

    /**
     * 校验菜单Id是否合法
     *
     * @param menuList 菜单id集合
     * @param identity 当前登录用户的身份
     * @return
     */
    boolean verifyMenu(List<Long> menuList, LoginIdentity identity);

    /**
     * 查询菜单类别对应的菜单
     *
     * @param menuCategory 菜单类别
     * @return 结果
     */
    List<SysMenu> getMenuByMenuCategory(MenuCategory menuCategory);

    /**
     * 获取菜单
     *
     * @return 结果
     */
    List<MenuBO> getMenuList();

    /**
     * 根据类型获取菜单
     *
     * @param type 菜单类型
     * @return 结果
     */
    List<SysMenu> getMenusByType(MenuCategory type);

    /**
     * 获取模块
     *
     * @return 结果
     */
    List<LogOperateBO> getModuleList();

    /**
     * 获取菜单
     *
     * @param id 模块id
     * @return 结果
     */
    List<LogOperateBO> getMenuList(Long id);
}
