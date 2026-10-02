package com.zhenshu.system.remote.ruoyi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.enums.base.AssociationType;
import com.zhenshu.system.business.ruoyi.domain.bo.LoginUserBO;
import com.zhenshu.system.business.ruoyi.domain.vo.LoginUserQueryVO;
import com.zhenshu.system.business.ruoyi.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/14 11:43
 * @desc
 */
@Service
public class RemoteSysUserService {
    @Resource
    private ISysUserService userService;

    /**
     * 根据登录用户关联的用户修改手机号
     *
     *
     * @param name  姓名
     * @param phone 手机号
     * @param id    关联的id
     * @param type  关联的类型
     * @return
     */
    public boolean updatePhoneByAssociationId(String name, String phone, Long id, AssociationType type) {
        return userService.updatePhoneByAssociationId(name, phone, id, type);
    }

    /**
     * 根据手机号码查询用户信息
     *
     * @param phone 手机号码
     * @return 结果
     */
    public SysUser selectByPhone(String phone) {
        return userService.selectByPhone(phone);
    }

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public boolean insertUser(SysUser user) {
        return userService.save(user);
    }

    /**
     * 根据 ID 选择修改
     *
     * @param sysUser 实体对象
     */
    public boolean updateById(SysUser sysUser) {
        return userService.updateById(sysUser);
    }

    /**
     * 根据手机号删除登录用户信息
     *
     * @param phone 手机号
     * @return 结果
     */
    public boolean deleteByPhone(String phone) {
        return userService.deleteByPhone(phone);
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    public int resetPwd(SysUser user) {
        return userService.resetPwd(user);
    }

    /**
     * 根据手机号修改密码
     *
     * @param phone    手机号
     * @param password 新密码
     * @return 结果
     */
    public int resetPwd(String phone, String password) {
        return userService.resetPwd(phone, password);
    }

    /**
     * 分页查询登录用户
     *
     * @param queryVO 入参
     * @return 结果
     */
    public IPage<LoginUserBO> getContactsListPage(LoginUserQueryVO queryVO) {
        return userService.getContactsListPage(queryVO);
    }

    /**
     * 根据Id查询
     *
     * @param userId iD
     * @return 结果
     */
    public SysUser selectById(Long userId) {
        return userService.getById(userId);
    }

    /**
     * 根据主键删除
     *
     * @param uid 登录用户id
     */
    public void deleteById(Long uid) {
        userService.removeById(uid);
    }

    /**
     * 禁用用户
     *
     * @param uid 登录用户id
     */
    public void disableById(Long uid) {
        userService.disableById(uid);
    }

    /**
     * 禁启用用户
     *
     * @param uid 登录用户id
     */
    public void enableById(Long uid) {
        userService.enableById(uid);
    }

    /**
     * 根据id获取
     *
     * @param id id
     * @return 结果
     */
    public SysUser getById(Long id) {
        return userService.getById(id);
    }

    }
