package com.zhenshu.system.business.ruoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.annotation.DataScope;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.constant.ScheduleConstants;
import com.zhenshu.common.constant.UserConstants;
import com.zhenshu.common.core.domain.entity.SysMenu;
import com.zhenshu.common.core.domain.entity.SysRole;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.enums.base.MenuCategory;
import com.zhenshu.common.enums.base.RoleType;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.DateUtils;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.StringUtils;
import com.zhenshu.common.utils.bean.BeanUtils;
import com.zhenshu.common.utils.spring.SpringUtils;
import com.zhenshu.system.business.ruoyi.domain.SysRoleDept;
import com.zhenshu.system.business.ruoyi.domain.SysRoleMenu;
import com.zhenshu.system.business.ruoyi.domain.SysUserRole;
import com.zhenshu.system.business.ruoyi.domain.bo.*;
import com.zhenshu.system.business.ruoyi.domain.vo.*;
import com.zhenshu.system.business.ruoyi.mapper.SysRoleDeptMapper;
import com.zhenshu.system.business.ruoyi.mapper.SysRoleMapper;
import com.zhenshu.system.business.ruoyi.mapper.SysRoleMenuMapper;
import com.zhenshu.system.business.ruoyi.mapper.SysUserRoleMapper;
import com.zhenshu.system.business.ruoyi.service.ISysMenuService;
import com.zhenshu.system.business.ruoyi.service.ISysRoleMenuService;
import com.zhenshu.system.business.ruoyi.service.ISysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Resource
    private SysRoleMenuMapper roleMenuMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private SysRoleDeptMapper roleDeptMapper;

    @Resource
    private ISysMenuService menuService;
    @Resource
    private ISysRoleMenuService roleMenuService;

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysRole> selectRoleList(SysRole role) {
        return baseMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        List<SysRole> userRoles = baseMapper.selectRolePermissionByUserId(userId);
        List<SysRole> roles = selectRoleAll();
        for (SysRole role : roles) {
            for (SysRole userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = baseMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll() {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRole());
    }

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Long> selectRoleListByUserId(Long userId) {
        return baseMapper.selectRoleListByUserId(userId);
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRole selectRoleById(Long roleId) {
        return baseMapper.selectRoleById(roleId);
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(SysRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = baseMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleKeyUnique(SysRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = baseMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    @Override
    public void checkRoleAllowed(SysRole role) {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin()) {
            throw new ServiceException("不允许操作超级管理员角色");
        }
    }

    /**
     * 校验角色是否有数据权限
     *
     * @param roleId 角色id
     */
    @Override
    public void checkRoleDataScope(Long roleId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysRole role = new SysRole();
            role.setRoleId(roleId);
            List<SysRole> roles = SpringUtils.getAopProxy(this).selectRoleList(role);
            if (StringUtils.isEmpty(roles)) {
                throw new ServiceException("没有权限访问角色数据！");
            }
        }
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRole(SysRole role) {
        // 新增角色信息
        baseMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRole(SysRole role) {
        // 修改角色信息
        baseMapper.updateRole(role);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int updateRoleStatus(SysRole role) {
        return baseMapper.updateRole(role);
    }

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int authDataScope(SysRole role) {
        // 修改角色信息
        baseMapper.updateRole(role);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(role);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(SysRole role) {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role 角色对象
     */
    public int insertRoleDept(SysRole role) {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<SysRoleDept> list = new ArrayList<SysRoleDept>();
        for (Long deptId : role.getDeptIds()) {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0) {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteRoleById(Long roleId) {
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(roleId);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(roleId);
        return baseMapper.deleteRoleById(roleId);
    }

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteRoleByIds(Long[] roleIds) {
        for (Long roleId : roleIds) {
            checkRoleAllowed(new SysRole(roleId));
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenu(roleIds);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDept(roleIds);
        return baseMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public int deleteAuthUser(SysUserRole userRole) {
        return userRoleMapper.deleteUserRoleInfo(userRole);
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    @Override
    public int deleteAuthUsers(Long roleId, Long[] userIds) {
        return userRoleMapper.deleteUserRoleInfos(roleId, userIds);
    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    @Override
    public int insertAuthUsers(Long roleId, Long[] userIds) {
        // 新增用户与角色管理
        List<SysUserRole> list = new ArrayList<>();
        for (Long userId : userIds) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userRoleMapper.batchUserRole(list);
    }

    /**
     * 检验角色Id是否合法
     *
     * @param type    关联类型
     * @param roleIds 角色Id
     */
    @Override
    public boolean verifyRole(RoleType type, List<Long> roleIds) {
        // 查询当前登录用户所在集团是否能查询到postIds中的所有数据, 如果返回的count等于postIds的长度返回true
        Long associationId = null;
        if (type == RoleType.BLOC) {
            associationId = SecurityUtils.getUserBlocId();
        } else if (type == RoleType.KG) {
            associationId = SecurityUtils.getUserKgId();
        }
        int count = super.count(
                new QueryWrapper<SysRole>().lambda()
                        .eq(SysRole::getRoleType, type)
                        .eq(SysRole::getAssociationId, associationId)
                        .eq(SysRole::getStatus, ScheduleConstants.Status.NORMAL.getValue())
                        .in(SysRole::getRoleId, roleIds)
        );
        return count == roleIds.size();
    }

    /**
     * 根据登录用户查询角色列表
     */
    @Override
    public List<RoleBO> getRoleList() {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysRole::getStatus, UserConstants.NORMAL)
                .orderByDesc(SysRole::getCreateTime);
        // 1.根据用户身份设置查询条件
        LoginIdentity userIdentity = SecurityUtils.getLoginIdentity();
        if (userIdentity.equals(LoginIdentity.BLOC_ADMIN) || userIdentity.equals(LoginIdentity.BLOC_PEOPLE)) {
            // 集团人员只能查询集团角色
            queryWrapper.lambda()
                    .eq(SysRole::getRoleType, RoleType.BLOC)
                    .eq(SysRole::getAssociationId, SecurityUtils.getUserBlocId());
        } else if (userIdentity.equals(LoginIdentity.KG_ADMIN) || userIdentity.equals(LoginIdentity.KG_PEOPLE)) {
            // 校区人员只能查询校区角色
            queryWrapper.lambda()
                    .eq(SysRole::getRoleType, RoleType.KG)
                    .eq(SysRole::getAssociationId, SecurityUtils.getUserKgId());
        }
        // 2.po转bo
        return super.list(queryWrapper).stream().map(item -> {
            RoleBO bo = new RoleBO();
            BeanUtils.copyBeanProp(bo, item);
            return bo;
        }).collect(Collectors.toList());
    }

    /**
     * 分页查询岗位
     *
     * @param queryVO 查询入参
     * @return 结果
     */
    @Override
    public IPage<PostBO> blocListPage(PostQueryVO queryVO) {
        IPage<PostBO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        SysUser login = SecurityUtils.getUser();
        LoginIdentity identity = login.getIdentity();
        RoleType roleType = null;
        Long associationId = null;
        if (LoginIdentity.BLOC_ADMIN.equals(identity) || LoginIdentity.BLOC_PEOPLE.equals(identity)) {
            roleType = RoleType.BLOC;
            associationId = login.getBlocId();
        } else if (LoginIdentity.KG_ADMIN.equals(identity) || LoginIdentity.KG_PEOPLE.equals(identity)) {
            roleType = RoleType.KG;
            associationId = login.getKgId();
        }
        return baseMapper.blocListPage(page, queryVO, roleType, associationId);
    }

    /**
     * 添加岗位
     *
     * @param addVO 添加入参
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(PostAddVO addVO) {
        SysUser login = SecurityUtils.getUser();
        // 1.校验需要关联的菜单是否合法
        if (!menuService.verifyMenu(addVO.getMenuList(), login.getIdentity())) {
            throw new ServiceException(ErrorEnums.MENU_ILLEGALITY);
        }
        // 2.新增岗位信息
        LoginIdentity identity = login.getIdentity();
        RoleType roleType = null;
        Long associationId = null;
        if (identity == LoginIdentity.BLOC_ADMIN || identity == LoginIdentity.BLOC_PEOPLE) {
            roleType = RoleType.BLOC;
            associationId = login.getBlocId();
        } else if (LoginIdentity.isKg(identity)) {
            roleType = RoleType.KG;
            associationId = login.getKgId();
        }
        SysRole sysRole = new SysRole();
        sysRole.setCreateBy(login.getUserId().toString());
        sysRole.setCreateTime(DateUtils.getNowDate());
        // 暂时设置空字符串
        sysRole.setRoleKey("");
        // 暂时设置为零
        sysRole.setRoleSort(Constants.ZERO);
        sysRole.setStatus(UserConstants.NORMAL);
        sysRole.setRoleType(roleType);
        sysRole.setAssociationId(associationId);
        sysRole.setRoleName(addVO.getPostName());
        super.save(sysRole);
        // 3.关联岗位和菜单的关系
        joinRoleMenu(sysRole.getRoleId(), addVO.getMenuList());
    }

    /**
     * 修改集团岗位
     *
     * @param editVO 修改入参
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRole(PostEditVO editVO) {
        // 1.根据Id查询岗位
        SysRole sysRole = this.getSysRoleByIdAndVerify(editVO.getPostId());
        // 2.校验需要关联的菜单是否合法
        LoginIdentity identity = SecurityUtils.getLoginIdentity();
        if (!menuService.verifyMenu(editVO.getMenuList(), identity)) {
            throw new ServiceException(ErrorEnums.MENU_ILLEGALITY);
        }
        // 3.修改岗位
        SysRole update = new SysRole();
        update.setRoleId(editVO.getPostId());
        update.setRoleName(editVO.getPostName());
        update.setUpdateTime(DateUtils.getNowDate());
        update.setUpdateBy(SecurityUtils.getUserId().toString());
        baseMapper.updateById(update);
        // 4.删除岗位与菜单的关联
        roleMenuService.deleteRoleMenuByRoleId(sysRole.getRoleId());
        // 5.关联岗位和菜单的关系
        joinRoleMenu(sysRole.getRoleId(), editVO.getMenuList());
    }

    /**
     * 根据Id查询岗位
     *
     * @param roleId 岗位Id
     * @return 结果
     */
    @Override
    public PostDetailsBO getDetailsById(Long roleId) {
        // 1.查询岗位
        SysRole sysRole = this.getSysRoleByIdAndVerify(roleId);
        // 2.查询岗位已选择的菜单
        List<SysRoleMenu> sysRoleMenus = roleMenuService.getRolePickMenu(roleId);
        Set<Long> menuIdSet = sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet());
        // 3.查询岗位类型对应的菜单
        MenuCategory category = null;
        if (sysRole.getRoleType() == RoleType.BLOC) {
            category = MenuCategory.BLOC;
        } else if (sysRole.getRoleType() == RoleType.KG) {
            category = MenuCategory.KG;
        }
        List<SysMenu> sysMenus = menuService.getMenuByMenuCategory(category);
        // 4.整理已查询到的菜单
        PostDetailsBO bo = new PostDetailsBO();
        bo.setPostName(sysRole.getRoleName());
        bo.setMenuList(new ArrayList<>());
        bo.setCheckedPosts(menuIdSet);
        Map<Long, MenuBO> map = new HashMap<>();
        for (SysMenu sysMenu : sysMenus) {
            MenuBO menu = new MenuBO();
            menu.setMenuId(sysMenu.getMenuId());
            menu.setMenuName(sysMenu.getMenuName());
            menu.setIsChecked(menuIdSet.contains(sysMenu.getMenuId()));
            map.put(sysMenu.getMenuId(), menu);
            if (sysMenu.getParentId() == null || sysMenu.getParentId().equals((long) Constants.ZERO)) {
                bo.getMenuList().add(menu);
            } else {
                MenuBO parent = map.get(sysMenu.getParentId());
                if (parent.getChildList() == null) {
                    parent.setChildList(new ArrayList<>());
                }
                parent.getChildList().add(menu);
            }
        }
        return bo;
    }

    /**
     * 根据Id删除岗位
     *
     * @param deleteVO 删除入参
     */
    @Override
    public void deleteById(PostDeleteVO deleteVO) {
        // 1.查询岗位
        SysRole sysRole = this.getSysRoleByIdAndVerify(deleteVO.getPostId());
        // 2.判断是否有用户绑定当前岗位
        if (sysRole.getBindCount() > Constants.ZERO) {
            throw new ServiceException(ErrorEnums.POST_HAS_PEOPLE);
        }
        // 3.不能保证角色表中的bind_count字段一定可靠, 再查询一次
        int count = userRoleMapper.countUserRoleByRoleId(sysRole.getRoleId());
        if (count > Constants.ZERO) {
            throw new ServiceException(ErrorEnums.POST_HAS_PEOPLE);
        }
        // 4.删除岗位
        SysRole update = new SysRole();
        update.setUpdateBy(SecurityUtils.getUserId().toString());
        update.setUpdateTime(DateUtils.getNowDate());
        super.update(update,
                new UpdateWrapper<SysRole>().lambda()
                        .eq(SysRole::getRoleId, sysRole.getRoleId())
                        .set(SysRole::getDelFlag, Constants.RY_DEL_FLAG_DELETE)
                        .set(SysRole::getUpdateBy, SecurityUtils.getUserId())
                        .set(SysRole::getUpdateTime, DateUtils.getNowDate())
        );
    }

    /**
     * 自增岗位的绑定数量
     *
     * @param roleIds 岗位id集合
     */
    @Override
    public void incrRoleBindCount(List<Long> roleIds) {
        for (Long roleId : roleIds) {
            super.update(
                    new UpdateWrapper<SysRole>().lambda()
                            .eq(SysRole::getRoleId, roleId)
                            .setSql(Constants.INCR_ROLE_BIND_COUNT)
            );
        }
    }

    /**
     * 自减岗位的绑定数量
     *
     * @param roleIds 岗位id集合
     */
    @Override
    public void decrRoleBindCount(Collection<Long> roleIds) {
        super.update(
                new UpdateWrapper<SysRole>().lambda()
                        .in(SysRole::getRoleId, roleIds)
                        .setSql(Constants.DECR_ROLE_BIND_COUNT)
        );
    }

    /**
     * 修改集团岗位状态
     *
     * @param statusVO 修改集团岗位状态入参
     */
    @Override
    public void updateStatusById(PostStatusVO statusVO) {
        // 1.查询岗位
        SysRole sysRole = this.getSysRoleByIdAndVerify(statusVO.getPostId());
        // 2.修改状态
        super.update(
                new UpdateWrapper<SysRole>().lambda()
                        .eq(SysRole::getRoleId, sysRole.getRoleId())
                        .set(SysRole::getStatus, statusVO.getStatus())
        );
    }

    /**
     * 根据岗位名称列表查询岗位
     *
     * @param postSet 岗位名称集合
     */
    @Override
    public List<RoleBO> selectByPostNames(Set<String> postSet) {
        // 1.根据登录用户的身份选择不同的查询条件
        SysUser login = SecurityUtils.getUser();
        LoginIdentity identity = login.getIdentity();
        RoleType type = null;
        Long associationId = null;
        if (identity == LoginIdentity.BLOC_ADMIN || identity == LoginIdentity.BLOC_PEOPLE) {
            type = RoleType.BLOC;
            associationId = login.getBlocId();
        } else if (LoginIdentity.isKg(identity)) {
            type = RoleType.KG;
            associationId = login.getKgId();
        }
        // 2.查询岗位
        List<SysRole> list = super.list(
                new QueryWrapper<SysRole>().lambda()
                        .in(SysRole::getRoleName, postSet)
                        .eq(SysRole::getRoleType, type)
                        .eq(SysRole::getAssociationId, associationId)
                        .select(SysRole::getRoleId, SysRole::getRoleName)
        );
        // 3.对象转换
        return list.stream().map(item -> {
            RoleBO bo = new RoleBO();
            bo.setRoleId(item.getRoleId());
            bo.setRoleName(item.getRoleName());
            return bo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取用户岗位名称
     *
     * @param uid 用户id
     * @return 结果
     */
    @Override
    public List<PostRoleNameBO> getPostName(List<Long> uid) {
        return baseMapper.getPostRoleName(uid);
    }

    /**
     * 根据角色id获取角色并校验当前用户是否能操作这个角色
     *
     * @param roleId 角色Id
     * @return 结果
     */
    public SysRole getSysRoleByIdAndVerify(Long roleId) {
        // 1.根据Id查询岗位
        SysRole sysRole = super.getById(roleId);
        if (sysRole == null) {
            throw new ServiceException(ErrorEnums.POST_NOT_EXIST);
        }
        RoleType roleType = null;
        Long associationId = null;
        LoginIdentity identity = SecurityUtils.getLoginIdentity();
        if (LoginIdentity.BLOC_ADMIN.equals(identity) || LoginIdentity.BLOC_PEOPLE.equals(identity)) {
            roleType = RoleType.BLOC;
            associationId = SecurityUtils.getUserBlocId();
        } else if (LoginIdentity.KG_ADMIN.equals(identity) || LoginIdentity.KG_PEOPLE.equals(identity)) {
            roleType = RoleType.KG;
            associationId = SecurityUtils.getUserKgId();
        }
        // 2.判断当前登录用户是否能操作这个岗位
        if (!sysRole.getRoleType().equals(roleType) || !sysRole.getAssociationId().equals(associationId)) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        return sysRole;
    }

    /**
     * 关联角色与菜单的关系
     *
     * @param roleId   角色Id
     * @param menuList 关联的菜单Id集合
     */
    private void joinRoleMenu(Long roleId, List<Long> menuList) {
        List<SysRoleMenu> list = menuList.stream().map(item -> {
            SysRoleMenu data = new SysRoleMenu();
            data.setRoleId(roleId);
            data.setMenuId(item);
            return data;
        }).collect(Collectors.toList());
        roleMenuService.batchRoleMenu(list);
    }
}
