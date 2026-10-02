package com.zhenshu.system.remote.ruoyi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.enums.base.RoleType;
import com.zhenshu.system.business.ruoyi.domain.bo.PostBO;
import com.zhenshu.system.business.ruoyi.domain.bo.PostDetailsBO;
import com.zhenshu.system.business.ruoyi.domain.bo.PostRoleNameBO;
import com.zhenshu.system.business.ruoyi.domain.vo.*;
import com.zhenshu.system.business.ruoyi.domain.bo.RoleBO;
import com.zhenshu.system.business.ruoyi.service.ISysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/15 10:10
 * @desc
 */
@Service
public class RemoteSysPostService {
    @Resource
    private ISysRoleService sysRoleService;

    /**
     * 校验岗位是否合法;
     *
     * @param postIds 岗位ID
     * @return 结果
     */
    public boolean verifyPost(RoleType type, List<Long> postIds) {
        return sysRoleService.verifyRole(type, postIds);
    }

    /**
     * 根据登录用户查询角色列表
     */
    public List<RoleBO> getPostList() {
        return sysRoleService.getRoleList();
    }

    /**
     * 分页查询岗位
     *
     * @param queryVO 查询入参
     * @return 结果
     */
    public IPage<PostBO> blocListPage(PostQueryVO queryVO) {
        return sysRoleService.blocListPage(queryVO);
    }

    /**
     * 添加岗位
     *
     * @param addVO 添加入参
     */
    public void insert(PostAddVO addVO) {
        sysRoleService.addRole(addVO);
    }

    /**
     * 修改岗位
     *
     * @param editVO 修改入参
     */
    public void updateById(PostEditVO editVO) {
        sysRoleService.editRole(editVO);
    }

    /**
     * 根据Id查询岗位
     *
     * @param roleId 岗位Id
     * @return 结果
     */
    public PostDetailsBO getDetailsById(Long roleId) {
        return sysRoleService.getDetailsById(roleId);
    }

    /**
     * 根据Id删除岗位
     *
     * @param deleteVO 删除入参
     */
    public void deleteById(PostDeleteVO deleteVO) {
        sysRoleService.deleteById(deleteVO);
    }

    /**
     * 修改集团岗位状态
     *
     * @param statusVO 修改集团岗位状态入参
     */
    public void updateStatusById(PostStatusVO statusVO) {
        sysRoleService.updateStatusById(statusVO);
    }

    /**
     * 根据岗位名称列表查询岗位
     *
     * @param postSet 岗位名称集合
     */
    public List<RoleBO> selectByPostNames(Set<String> postSet) {
        return sysRoleService.selectByPostNames(postSet);
    }

    public List<PostRoleNameBO> getPostName(List<Long> uid){
        return sysRoleService.getPostName(uid);
    }
}
