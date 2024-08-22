package com.zhenshu.framework.web.service;

import java.util.HashSet;
import java.util.Set;

import com.zhenshu.common.enums.base.*;
import com.zhenshu.system.business.bloc.base.domain.po.BlocStaffKindergarten;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffKindergartenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.system.business.ruoyi.service.ISysMenuService;
import com.zhenshu.system.business.ruoyi.service.ISysRoleService;

import javax.annotation.Resource;

/**
 * 用户权限处理
 *
 * @author ruoyi
 */
@Component
public class SysPermissionService {
    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysMenuService menuService;

    @Resource
    private IBlocStaffKindergartenService blocStaffKindergartenService;

    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            roles.add("admin");
        } else if (LoginIdentity.BLOC_TO_KG.equals(user.getIdentity())) {
            // 集团人员进入校区的权限绑定的是blocStaffKindergarten的id, 所以先查blocStaffKindergarten表的数据
            BlocStaffKindergarten blocStaffKindergarten = blocStaffKindergartenService.getByBlocStaffIdAndKgId(user.getAssociationId(), user.getKgId());
            roles.addAll(roleService.selectRolePermissionByUserId(blocStaffKindergarten.getId()));
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser user) {
        Set<String> perms = new HashSet<>();
        LoginIdentity identity = user.getIdentity();
        /*
         * 1、平台人员，拥有所有权限
         * 2、集团管理员，集团管理员包括集团所有权限和园区所有权限
         * 3、集团人员 判断现在的状态是在集团还是在园区，在集团有该集团的权限，园区有该园区的权限
         * 4、园区管理员，拥有园区的所有菜单权限
         * 5、园区人员，拥有他在这个园区的所有权限
         * 6、系统管理员拥有所有权限，保留原本的系统管理员权限，但原本若依系统的其他非管理员就没有权限了
         */
        if (LoginIdentity.PLATFORM.equals(identity)) {
            perms.add("*:*:*");
        } else if (LoginIdentity.BLOC_ADMIN.equals(identity)) {
            int[] categoryArr = {MenuCategory.BLOC.getValue(), MenuCategory.KG.getValue()};
            perms.addAll(menuService.selectPermsByCategory(categoryArr));
        } else if (LoginIdentity.BLOC_PEOPLE.equals(identity)) {
            this.getBlocStaffPerm(user, perms);
        } else if (LoginIdentity.KG_ADMIN.equals(identity)) {
            int[] categoryArr = {MenuCategory.KG.getValue()};
            perms.addAll(menuService.selectPermsByCategory(categoryArr));
        } else if (LoginIdentity.KG_PEOPLE.equals(identity)) {
            perms.addAll(menuService.selectPermsByUserIdAndType(user.getUserId(), UserRoleType.KG_PEOPLE.getValue()));
        } else if (LoginIdentity.BLOC_TO_KG.equals(identity)) {
            // 集团人员进入校区的权限绑定的是blocStaffKindergarten的id, 所以先查blocStaffKindergarten表的数据
            BlocStaffKindergarten blocStaffKindergarten = blocStaffKindergartenService.getByBlocStaffIdAndKgId(user.getAssociationId(), user.getKgId());
            perms.addAll(menuService.selectPermsByUserIdAndType(blocStaffKindergarten.getId(), UserRoleType.BLOC_PEOPLE_INTO_KG.getValue()));
        } else {
            if (user.isAdmin()) {
                perms.add("*:*:*");
            }
        }
        return perms;
    }

    private void getBlocStaffPerm(SysUser user, Set<String> perms) {
        if (BlocStaffStatus.BLOC.equals(user.getBlocStaffStatus())) {
            perms.addAll(menuService.selectPermsByUserIdAndType(user.getUserId(), UserRoleType.BLOC_PEOPLE.getValue()));
        } else if (BlocStaffStatus.INTO_KG.equals(user.getBlocStaffStatus())) {
            // 因为一个集团人员有多个校区的权限，所以需要通过集团人员和校区的关联表id来确认用户的权限
            perms.addAll(menuService.selectPermsByUserIdAndType(user.getBlocStaffKindergartenId(), UserRoleType.BLOC_PEOPLE_INTO_KG.getValue()));
        }
    }
}
