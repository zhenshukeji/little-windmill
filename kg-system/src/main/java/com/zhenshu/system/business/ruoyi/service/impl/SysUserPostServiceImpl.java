package com.zhenshu.system.business.ruoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.system.business.ruoyi.domain.SysUserPost;
import com.zhenshu.system.business.ruoyi.mapper.SysUserPostMapper;
import com.zhenshu.system.business.ruoyi.service.ISysUserPostService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/14 14:55
 * @desc
 */
@Service
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements ISysUserPostService {
    /**
     * 通过用户ID删除用户和岗位关联
     *
     * @param uid 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserPostByUserId(Long uid) {
        return baseMapper.deleteUserPostByUserId(uid);
    }

    /**
     * 批量新增用户岗位信息
     *
     * @param sysUserPosts 用户角色列表
     * @return 结果
     */
    @Override
    public void batchUserPost(List<SysUserPost> sysUserPosts) {
        baseMapper.batchUserPost(sysUserPosts);
    }

    /**
     * 查询用户的所有岗位
     *
     * @param uid
     * @return
     */
    @Override
    public List<SysUserPost> selectList(Long uid) {
        return super.list(
                new QueryWrapper<SysUserPost>().lambda()
                        .eq(SysUserPost::getUserId, uid)
        );
    }
}
