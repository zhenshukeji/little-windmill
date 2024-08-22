package com.zhenshu.system.business.ruoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.system.business.ruoyi.domain.SysUserPost;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/14 14:55
 * @desc
 */
public interface ISysUserPostService extends IService<SysUserPost> {
    /**
     * 通过用户ID删除用户和岗位关联
     *
     * @param uid 用户ID
     * @return 结果
     */
    int deleteUserPostByUserId(Long uid);


    /**
     * 批量新增用户岗位信息
     *
     * @param sysUserPosts 用户角色列表
     * @return 结果
     */
    void batchUserPost(List<SysUserPost> sysUserPosts);


    /**
     * 查询用户的所有岗位
     *
     * @param uid
     * @return
     */
    List<SysUserPost> selectList(Long uid);
}
