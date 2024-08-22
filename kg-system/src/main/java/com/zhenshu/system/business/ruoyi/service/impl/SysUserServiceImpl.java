package com.zhenshu.system.business.ruoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.annotation.DataScope;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.constant.UserConstants;
import com.zhenshu.common.core.domain.entity.SysRole;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.enums.base.AssociationType;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.DateUtils;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.StringUtils;
import com.zhenshu.common.utils.bean.BeanValidators;
import com.zhenshu.common.utils.spring.SpringUtils;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffService;
import com.zhenshu.system.business.kg.work.educational.domain.vo.ContactsQueryVO;
import com.zhenshu.system.business.ruoyi.domain.SysPost;
import com.zhenshu.system.business.ruoyi.domain.SysUserPost;
import com.zhenshu.system.business.ruoyi.domain.SysUserRole;
import com.zhenshu.system.business.ruoyi.domain.bo.LoginUserBO;
import com.zhenshu.system.business.ruoyi.domain.vo.LoginUserQueryVO;
import com.zhenshu.system.business.ruoyi.mapper.*;
import com.zhenshu.system.business.ruoyi.service.ISysConfigService;
import com.zhenshu.system.business.ruoyi.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource
    private SysUserMapper userMapper;

    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysPostMapper postMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private SysUserPostMapper userPostMapper;

    @Resource
    private ISysConfigService configService;

    @Resource
    protected Validator validator;

    @Resource
    private IBlocStaffService blocStaffService;

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user) {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(Long userId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users)) {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user) {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional
    public void insertUserAuth(Long userId, Long[] roleIds) {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user) {
        SysUser update = new SysUser();
        update.setUserId(user.getUserId());
        update.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        update.setUpdateBy(SecurityUtils.getUserId().toString());
        update.setUpdateTime(DateUtils.getNowDate());
        return super.updateById(update) ? Constants.ONE : Constants.ZERO;
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds) {
        if (StringUtils.isNotNull(roleIds)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roleIds) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u)) {
                    BeanValidators.validateWithException(validator, user);
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                } else if (isUpdateSupport) {
                    BeanValidators.validateWithException(validator, user);
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 根据手机号码查询用户信息
     *
     * @param phone 手机号码
     * @return 结果
     */
    @Override
    public SysUser selectByPhone(String phone) {
        return this.getOne(
                new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getPhonenumber, phone)
        );
    }

    /**
     * 根据登录用户关联的账号Id修改手机号
     *
     * @param name  姓名
     * @param phone 手机号
     * @param id    关联的id
     * @param type  关联的类型
     * @return
     */
    @Override
    public boolean updatePhoneByAssociationId(String name, String phone, Long id, AssociationType type) {
        // 1.根据关联的账户类型和账户Id查询登录用户
        SysUser sysUser = super.getOne(
                new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getAssociationId, id)
                        .eq(SysUser::getAssociationType, type)
        );
        // 2.判断登录用户是否存在
        if (sysUser == null) {
            throw new ServiceException(ErrorEnums.SYS_USER_NOT_EXIST);
        }
        // 3.修改登录用户手机号码
        SysUser update = new SysUser();
        update.setUserName(phone);
        update.setUserId(sysUser.getUserId());
        update.setPhonenumber(phone);
        update.setNickName(name);
        update.setUpdateBy(SecurityUtils.getUserId().toString());
        update.setUpdateTime(DateUtils.getNowDate());
        return super.updateById(update);
    }

    /**
     * 根据手机号删除登录用户信息
     *
     * @param phone 手机号
     * @return
     */
    @Override
    public boolean deleteByPhone(String phone) {
        // 1.根据手机号查询登录用户信息
        SysUser sysUser = super.getOne(
                new UpdateWrapper<SysUser>().lambda()
                        .eq(SysUser::getPhonenumber, phone)
        );
        // 2.判断用户信息是否存在
        if (sysUser == null) {
            throw new ServiceException(ErrorEnums.SYS_USER_NOT_EXIST);
        }
        // 3.根据登录用户Id逻辑删除
        SysUser update = new SysUser();
        update.setUpdateBy(SecurityUtils.getUserId().toString());
        update.setUpdateTime(DateUtils.getNowDate());
        update.setDelFlag(Constants.RY_DEL_FLAG_DELETE);
        return super.update(update,
                new UpdateWrapper<SysUser>().lambda()
                        .eq(SysUser::getUserId, sysUser.getUserId())
                        .set(SysUser::getDelFlag, Constants.RY_DEL_FLAG_DELETE)
        );
    }

    /**
     * 根据手机号修改密码
     *
     * @param phone    手机号
     * @param password 新密码
     * @return 结果
     */
    @Override
    public int resetPwd(String phone, String password) {
        // 1.查询登录用户
        SysUser sysUser = this.selectByPhone(phone);
        // 2.修改登录用户的密码
        sysUser.setPassword(password);
        return this.resetPwd(sysUser);
    }

    /**
     * 分页查询登录用户
     *
     * @param queryVO 入参
     * @return 结果
     */
    @Override
    public IPage<LoginUserBO> getContactsListPage(LoginUserQueryVO queryVO) {
        SysUser user = SecurityUtils.getUser();
        LoginIdentity identity = user.getIdentity();
        IPage<LoginUserBO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        Long param = null;
        AssociationType type = null;
        // 查询时根据当前登录用户的登录身份关联不同的表
        if (LoginIdentity.isBloc(identity)) {
            param = user.getBlocId();
            type = AssociationType.BLOC;
        } else if (LoginIdentity.isKg(identity)) {
            param = user.getKgId();
            type = AssociationType.KG;
        }
        page = baseMapper.getLoginUserListPage(page, type, param, queryVO);
        // 处理posts字段
        for (LoginUserBO bo : page.getRecords()) {
            if (!CollectionUtils.isEmpty(bo.getPosts()) && StringUtils.isNotEmpty(bo.getPosts().get(Constants.ZERO))) {
                bo.setPosts(Arrays.stream(bo.getPosts().get(Constants.ZERO).split(",")).collect(Collectors.toList()));
            }
        }
        return page;
    }

    /**
     * 禁用用户
     *
     * @param uid 登录用户id
     */
    @Override
    public void disableById(Long uid) {
        SysUser update = new SysUser();
        update.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        update.setUpdateTime(DateUtils.getNowDate());
        update.setUserId(uid);
        update.setStatus(Constants.RY_DISABLE);
        super.updateById(update);
    }

    /**
     * 禁启用用户
     *
     * @param uid 登录用户id
     */
    @Override
    public void enableById(Long uid) {
        SysUser update = new SysUser();
        update.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        update.setUpdateTime(DateUtils.getNowDate());
        update.setUserId(uid);
        update.setStatus(Constants.RY_NORMAL);
        super.updateById(update);
    }

    /**
     * 分页查询
     *
     * @param queryVO 入参
     * @return 结果
     */
    @Override
    public IPage<LoginUserBO> getContactsListPage(ContactsQueryVO queryVO) {
        IPage<LoginUserBO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        page = baseMapper.getContactsListPage(page, queryVO);
        // 处理posts字段
        for (LoginUserBO bo : page.getRecords()) {
            if (!CollectionUtils.isEmpty(bo.getPosts()) && StringUtils.isNotEmpty(bo.getPosts().get(Constants.ZERO))) {
                bo.setPosts(Arrays.stream(bo.getPosts().get(Constants.ZERO).split(",")).collect(Collectors.toList()));
            }
        }
        return page;
    }
}
