package com.zhenshu.system.business.ruoyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.core.domain.entity.SysRole;
import com.zhenshu.common.enums.base.RoleType;
import com.zhenshu.system.business.ruoyi.domain.bo.PostBO;
import com.zhenshu.system.business.ruoyi.domain.bo.PostDetailsBO;
import com.zhenshu.system.business.ruoyi.domain.bo.PostRoleNameBO;
import com.zhenshu.system.business.ruoyi.domain.vo.*;
import com.zhenshu.system.business.ruoyi.domain.SysUserRole;
import com.zhenshu.system.business.ruoyi.domain.bo.RoleBO;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色业务层
 *
 * @author ruoyi
 */
public interface ISysRoleService {
    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    public List<SysRole> selectRoleList(SysRole role);

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    public List<SysRole> selectRolesByUserId(Long userId);

    /**
     * 根据用户ID查询角色权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectRolePermissionByUserId(Long userId);

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    public List<SysRole> selectRoleAll();

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    public List<Long> selectRoleListByUserId(Long userId);

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    public SysRole selectRoleById(Long roleId);

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    public String checkRoleNameUnique(SysRole role);

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    public String checkRoleKeyUnique(SysRole role);

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    public void checkRoleAllowed(SysRole role);

    /**
     * 校验角色是否有数据权限
     *
     * @param roleId 角色id
     */
    public void checkRoleDataScope(Long roleId);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int countUserRoleByRoleId(Long roleId);

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    public int insertRole(SysRole role);

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    public int updateRole(SysRole role);

    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    public int updateRoleStatus(SysRole role);

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    public int authDataScope(SysRole role);

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleById(Long roleId);

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    public int deleteRoleByIds(Long[] roleIds);

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    public int deleteAuthUser(SysUserRole userRole);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    public int deleteAuthUsers(Long roleId, Long[] userIds);

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    public int insertAuthUsers(Long roleId, Long[] userIds);

    /**
     * 检验角色Id是否合法
     *
     * @param type    角色类型
     * @param roleIds 角色Id
     */
    boolean verifyRole(RoleType type, List<Long> roleIds);

    /**
     * 根据登录用户查询角色列表
     */
    List<RoleBO> getRoleList();

    /**
     * 分页查询岗位
     *
     * @param queryVO 查询入参
     * @return 结果
     */
    IPage<PostBO> blocListPage(PostQueryVO queryVO);

    /**
     * 添加岗位
     *
     * @param addVO 添加入参
     */
    void addRole(PostAddVO addVO);

    /**
     * 修改集团岗位
     *
     * @param editVO 修改入参
     */
    void editRole(PostEditVO editVO);

    /**
     * 根据Id查询岗位
     *
     * @param roleId 岗位Id
     * @return 结果
     */
    PostDetailsBO getDetailsById(Long roleId);

    /**
     * 根据Id删除岗位
     *
     * @param deleteVO 删除入参
     */
    void deleteById(PostDeleteVO deleteVO);

    /**
     * 自增岗位的绑定数量
     *
     * @param roleIds 岗位id集合
     */
    void incrRoleBindCount(List<Long> roleIds);

    /**
     * 自减岗位的绑定数量
     *
     * @param roleIds 岗位id集合
     */
    void decrRoleBindCount(Collection<Long> roleIds);

    /**
     * 修改集团岗位状态
     *
     * @param statusVO 修改集团岗位状态入参
     */
    void updateStatusById(PostStatusVO statusVO);

    /**
     * 根据岗位名称列表查询岗位
     *
     * @param postSet 岗位名称集合
     */
    List<RoleBO> selectByPostNames(Set<String> postSet);

    /**
     * 获取用户岗位名称
     *
     * @param uid 用户id
     * @return 结果
     */
    List<PostRoleNameBO> getPostName(List<Long> uid);
}
