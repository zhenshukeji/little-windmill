package com.zhenshu.system.business.ruoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.constant.UserConstants;
import com.zhenshu.common.core.domain.TreeSelect;
import com.zhenshu.common.core.domain.entity.SysMenu;
import com.zhenshu.common.core.domain.entity.SysRole;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.enums.base.*;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.StringUtils;
import com.zhenshu.system.business.bloc.base.domain.po.BlocStaffKindergarten;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffKindergartenService;
import com.zhenshu.system.business.ruoyi.domain.bo.LogOperateBO;
import com.zhenshu.system.business.ruoyi.domain.bo.MenuBO;
import com.zhenshu.system.business.ruoyi.domain.vo.MetaVo;
import com.zhenshu.system.business.ruoyi.domain.vo.RouterVo;
import com.zhenshu.system.business.ruoyi.mapper.SysMenuMapper;
import com.zhenshu.system.business.ruoyi.mapper.SysRoleMapper;
import com.zhenshu.system.business.ruoyi.mapper.SysRoleMenuMapper;
import com.zhenshu.system.business.ruoyi.service.ISysMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Resource
    private SysMenuMapper menuMapper;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysRoleMenuMapper roleMenuMapper;
    @Resource
    private IBlocStaffKindergartenService blocStaffKindergartenService;
    private static final Logger log = LoggerFactory.getLogger(SysMenuServiceImpl.class);

    /**
     * 根据用户信息获取菜单列表
     *
     * @param user 用户信息
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuTreeByUser(SysUser user) {
        Long userId = user.getUserId();
        List<SysMenu> menus = null;
        /*
         * 1、系统管理员展示所有菜单
         * 2、平台人员判断当前登录状态，在平台展示平台菜单，在集团展示集团的菜单，在园区展示园区的菜单
         * 3、集团管理员判断当前登录状态，在集团展示集团的所有菜单，在园区展示园区的所有菜单
         * 4、集团人员判断当前登录状态，在集团展示该用户在集团里角色的拥有菜单，在园区展示该用户在园区里拥有的菜单
         * 5、园区管理员展示园区的所有菜单
         * 6、园区人员展示该用户在园区里的角色拥有的所有菜单
         */
        LoginIdentity identity = user.getIdentity();
        if (SecurityUtils.isAdmin(userId)) {
            menus = menuMapper.selectMenuTreeAll();
            return getChildPerms(menus, 0);
        } else if (LoginIdentity.PLATFORM.equals(identity)) {
            menus = this.getPlatformStaffMenu(user);
        } else if (LoginIdentity.BLOC_ADMIN.equals(identity)) {
            menus = this.getBlocAdminMenu(user);
        } else if (LoginIdentity.BLOC_PEOPLE.equals(identity)) {
            menus = this.getBlocPeopleMenu(user);
        } else if (LoginIdentity.KG_ADMIN.equals(identity)) {
            menus = this.selectMenuTreeByCategory(MenuCategory.KG.getValue());
        } else if (LoginIdentity.KG_PEOPLE.equals(identity)) {
            menus = this.selectMenuTreeByUserIdAndUserType(user.getUserId(), String.valueOf(UserRoleType.KG_PEOPLE.getValue()));
        } else if (LoginIdentity.BLOC_TO_KG.equals(identity)) {
            // 集团人员进入校区的权限绑定的是blocStaffKindergarten的id, 所以先查blocStaffKindergarten表的数据
            BlocStaffKindergarten blocStaffKindergarten = blocStaffKindergartenService.getByBlocStaffIdAndKgId(user.getAssociationId(), user.getKgId());
            menus = this.selectMenuTreeByUserIdAndUserType(blocStaffKindergarten.getId(), String.valueOf(UserRoleType.BLOC_PEOPLE_INTO_KG.getValue()));
        }
        return menus;
    }

    /**
     * 校验菜单Id是否合法
     *
     * @param menuList 菜单id集合
     * @param identity 当前登录用户的身份
     * @return
     */
    @Override
    public boolean verifyMenu(List<Long> menuList, LoginIdentity identity) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        if (LoginIdentity.BLOC_ADMIN.equals(identity) || LoginIdentity.BLOC_PEOPLE.equals(identity)) {
            queryWrapper.lambda().eq(SysMenu::getCategory, MenuCategory.BLOC);
        } else if (LoginIdentity.KG_ADMIN.equals(identity) || LoginIdentity.KG_PEOPLE.equals(identity)) {
            queryWrapper.lambda().eq(SysMenu::getCategory, MenuCategory.KG);
        }
        Integer count = super.count(
                queryWrapper.lambda()
                        .eq(SysMenu::getStatus, UserConstants.NORMAL)
                        .in(SysMenu::getMenuId, menuList)
        );
        return Objects.equals(count, menuList.size());
    }

    /**
     * 查询菜单类别对应的菜单
     *
     * @param menuCategory 菜单类别
     * @return 结果
     */
    @Override
    public List<SysMenu> getMenuByMenuCategory(MenuCategory menuCategory) {
        return super.list(
                new QueryWrapper<SysMenu>().lambda()
                        .eq(SysMenu::getStatus, UserConstants.NORMAL)
                        .eq(SysMenu::getCategory, menuCategory)
                        .orderByAsc(SysMenu::getParentId)
                        .orderByAsc(SysMenu::getOrderNum)
        );
    }

    /**
     * 获取菜单
     *
     * @return 结果
     */
    @Override
    public List<MenuBO> getMenuList() {
        // 1.根据登录用户的身份查询菜单
        MenuCategory category = null;
        LoginIdentity identity = SecurityUtils.getLoginIdentity();
        if (identity == LoginIdentity.BLOC_ADMIN || identity == LoginIdentity.BLOC_PEOPLE) {
            category = MenuCategory.BLOC;
        } else if (LoginIdentity.isKg(identity)) {
            category = MenuCategory.KG;
        }
        List<SysMenu> menuList = this.getMenuByMenuCategory(category);
        // 2.整理菜单
        return this.formatMenu(menuList);
    }

    /**
     * 整理菜单
     *
     * @param menuList 菜单集合
     * @return 结果
     */
    private List<MenuBO> formatMenu(List<SysMenu> menuList) {
        ArrayList<MenuBO> list = new ArrayList<>(Constants.TEN);
        Map<Long, MenuBO> map = menuList.stream().collect(
                Collectors.toMap(SysMenu::getMenuId,
                        item -> {
                            MenuBO menu = new MenuBO();
                            menu.setMenuId(item.getMenuId());
                            menu.setMenuName(item.getMenuName());
                            return menu;
                        }
                )
        );
        for (SysMenu sysMenu : menuList) {
            MenuBO menu = map.get(sysMenu.getMenuId());
            if (sysMenu.getParentId() == null || sysMenu.getParentId().equals((long) Constants.ZERO)) {
                list.add(menu);
            } else {
                MenuBO parent = map.get(sysMenu.getParentId());
                if (parent.getChildList() == null) {
                    parent.setChildList(new ArrayList<>());
                }
                parent.getChildList().add(menu);
            }
        }
        return list;
    }

    /**
     * 根据类型获取菜单
     *
     * @param type 菜单类型
     * @return 结果
     */
    @Override
    public List<SysMenu> getMenusByType(MenuCategory type) {
        return this.getMenuByMenuCategory(type);
    }

    /**
     * 获取模块
     *
     * @return 结果
     */
    @Override
    public List<LogOperateBO> getModuleList() {
        MenuCategory category = this.getMenuCategory();
        return baseMapper.getModuleList(category);
    }

    /**
     * 获取菜单
     *
     * @param id 模块id
     * @return 结果
     */
    @Override
    public List<LogOperateBO> getMenuList(Long id) {
        MenuCategory category = this.getMenuCategory();
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        if (category != null) {
            queryWrapper.lambda().eq(SysMenu::getCategory, category);
        }
        List<SysMenu> list = super.list(
                queryWrapper.lambda()
                        .eq(SysMenu::getParentId, id)
                        .eq(SysMenu::getStatus, Constants.RY_NORMAL)
                        .eq(SysMenu::getVisible, Constants.RY_NORMAL)
                        .select(SysMenu::getMenuId, SysMenu::getMenuName)
        );
        return list.stream().map(item -> {
            LogOperateBO bo = new LogOperateBO();
            bo.setId(item.getMenuId());
            bo.setName(item.getMenuName());
            return bo;
        }).collect(Collectors.toList());
    }

    /**
     * @return
     */
    private MenuCategory getMenuCategory() {
        LoginIdentity identity = SecurityUtils.getLoginIdentity();
        MenuCategory category = null;
        if (identity == LoginIdentity.BLOC_TO_KG) {
            category = MenuCategory.KG;
        } else if (identity == LoginIdentity.KG_ADMIN || identity == LoginIdentity.KG_PEOPLE) {
            category = MenuCategory.KG;
        } else if (identity == LoginIdentity.BLOC_ADMIN || identity == LoginIdentity.BLOC_PEOPLE) {
            category = MenuCategory.BLOC;
        } else if (identity == LoginIdentity.PLATFORM) {
            category = MenuCategory.PLATFORM;
        }
        return category;
    }

    private List<SysMenu> getPlatformStaffMenu(SysUser user) {
        List<SysMenu> menus;
        if (PlatformStatus.PLATFORM.equals(user.getPlatformStatus())) {
            menus = this.selectMenuTreeByCategory(MenuCategory.PLATFORM.getValue());
        } else if (PlatformStatus.INTO_BLOC.equals(user.getPlatformStatus())) {
            menus = this.selectMenuTreeByCategory(MenuCategory.BLOC.getValue());
        } else if (PlatformStatus.INTO_KG.equals(user.getPlatformStatus())) {
            menus = this.selectMenuTreeByCategory(MenuCategory.KG.getValue());
        } else {
            log.warn("平台人员登录状态不正确，状态是 {}", user.getPlatformStatus().getValue());
            throw new ServiceException(ErrorEnums.LOGIN_ERROR);
        }
        return menus;
    }

    private List<SysMenu> getBlocAdminMenu(SysUser user) {
        List<SysMenu> menus;
        if (BlocStaffStatus.BLOC.equals(user.getBlocStaffStatus())) {
            menus = this.selectMenuTreeByCategory(MenuCategory.BLOC.getValue());
        } else if (BlocStaffStatus.INTO_KG.equals(user.getBlocStaffStatus())) {
            menus = this.selectMenuTreeByCategory(MenuCategory.KG.getValue());
        } else {
            log.warn("集团管理员登录状态不正确，状态是 {}", user.getPlatformStatus().getValue());
            throw new ServiceException(ErrorEnums.LOGIN_ERROR);
        }
        return menus;
    }

    private List<SysMenu> getBlocPeopleMenu(SysUser user) {
        List<SysMenu> menus;
        if (BlocStaffStatus.BLOC.equals(user.getBlocStaffStatus())) {
            menus = this.selectMenuTreeByUserIdAndUserType(user.getUserId(), UserRoleType.BLOC_PEOPLE.getInfo());
        } else if (BlocStaffStatus.INTO_KG.equals(user.getBlocStaffStatus())) {
            menus = this.selectMenuTreeByUserIdAndUserType(user.getBlocStaffKindergartenId(), UserRoleType.BLOC_PEOPLE_INTO_KG.getInfo());
        } else {
            log.warn("集团管理员登录状态不正确，状态是 {}", user.getPlatformStatus().getValue());
            throw new ServiceException(ErrorEnums.LOGIN_ERROR);
        }
        return menus;
    }

    private List<SysMenu> selectMenuTreeByUserIdAndUserType(Long userId, String userType) {
        List<SysMenu> menus = this.menuMapper.selectMenuTreeByUserIdAndUserType(userId, userType);
        return getChildPerms(menus, 0);
    }


    private List<SysMenu> selectMenuTreeByCategory(Integer category) {
        List<SysMenu> menus = this.menuMapper.selectMenuTreeByCategory(category);
        return getChildPerms(menus, 0);
    }

    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(Long userId) {
        return selectMenuList(new SysMenu(), userId);
    }

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
        List<SysMenu> menuList = null;
        // 管理员显示所有菜单信息
        if (SysUser.isAdmin(userId)) {
            menuList = menuMapper.selectMenuList(menu);
        } else {
            menu.getParams().put("userId", userId);
            menuList = menuMapper.selectMenuListByUserId(menu);
        }
        return menuList;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> menus = null;
        if (SecurityUtils.isAdmin(userId)) {
            menus = menuMapper.selectMenuTreeAll();
        } else {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, 0);
    }

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        SysRole role = roleMapper.selectRoleById(roleId);
        return menuMapper.selectMenuListByRoleId(roleId, role.isMenuCheckStrictly());
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setId(menu.getMenuId());
            router.setHidden("1".equals(menu.getVisible()));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQuery());
            router.setPermission(menu.getPerms());
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
            List<SysMenu> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (isMenuFrame(menu)) {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setId(menu.getMenuId());
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
                children.setQuery(menu.getQuery());
                children.setPermission(menu.getPerms());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId().intValue() == 0 && isInnerLink(menu)) {
                router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                router.setPath("/inner");
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setId(menu.getMenuId());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                children.setPermission(menu.getPerms());
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysMenu dept : menus) {
            tempList.add(dept.getMenuId());
        }
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext(); ) {
            SysMenu menu = (SysMenu) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
        List<SysMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenu selectMenuById(Long menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean hasChildByMenuId(Long menuId) {
        int result = menuMapper.hasChildByMenuId(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean checkMenuExistRole(Long menuId) {
        int result = roleMenuMapper.checkMenuExistRole(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(SysMenu menu) {
        return menuMapper.insertMenu(menu);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(SysMenu menu) {
        return menuMapper.updateMenu(menu);
    }

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuById(Long menuId) {
        return menuMapper.deleteMenuById(menuId);
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public String checkMenuNameUnique(SysMenu menu) {
        Long menuId = StringUtils.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
        SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (StringUtils.isNotNull(info) && info.getMenuId().longValue() != menuId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public Set<String> selectPermsByCategory(int[] categoryArr) {
        List<String> perms = menuMapper.selectMenuPermsByCategory(categoryArr);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public Set<String> selectPermsByUserIdAndType(Long userId, Integer userType) {
        List<String> perms = menuMapper.selectPermsByUserIdAndType(userId, userType);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenu menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame())) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenu menu) {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            component = UserConstants.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(SysMenu menu) {
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(UserConstants.NO_FRAME);
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(SysMenu menu) {
        return menu.getIsFrame().equals(UserConstants.NO_FRAME) && StringUtils.ishttp(menu.getPath());
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenu menu) {
        return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext(); ) {
            SysMenu t = (SysMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return
     */
    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS},
                new String[]{"", ""});
    }
}
