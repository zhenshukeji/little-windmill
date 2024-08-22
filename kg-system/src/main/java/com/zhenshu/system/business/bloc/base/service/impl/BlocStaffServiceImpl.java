package com.zhenshu.system.business.bloc.base.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.core.domain.model.LoginUser;
import com.zhenshu.common.enums.base.AssociationType;
import com.zhenshu.common.enums.base.RoleType;
import com.zhenshu.common.enums.base.UserIdentity;
import com.zhenshu.common.enums.base.UserRoleType;
import com.zhenshu.common.enums.system.UserSex;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.DateUtils;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.StringUtils;
import com.zhenshu.common.utils.bean.BeanUtils;
import com.zhenshu.system.business.bloc.base.domain.bo.BlocStaffBO;
import com.zhenshu.system.business.bloc.base.domain.bo.BlocStaffDetailsBO;
import com.zhenshu.system.business.bloc.base.domain.po.BlocStaff;
import com.zhenshu.system.business.bloc.base.domain.po.BlocStaffKindergarten;
import com.zhenshu.system.business.bloc.base.domain.vo.*;
import com.zhenshu.system.business.bloc.base.mapper.BlocStaffMapper;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffKindergartenService;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffService;
import com.zhenshu.system.business.platform.service.IBlocService;
import com.zhenshu.system.business.ruoyi.domain.SysUserRole;
import com.zhenshu.system.business.ruoyi.domain.vo.UserIdVO;
import com.zhenshu.system.remote.ruoyi.RemoteCommonService;
import com.zhenshu.system.remote.ruoyi.RemoteSysUserPostService;
import com.zhenshu.system.remote.ruoyi.RemoteSysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:58
 * @desc
 */
@Slf4j
@Service
public class BlocStaffServiceImpl extends ServiceImpl<BlocStaffMapper, BlocStaff> implements IBlocStaffService {
    @Resource
    private RemoteSysUserPostService remoteSysUserPostService;
    @Resource
    private RemoteSysUserService remoteSysUserService;
    @Resource
    private IBlocStaffKindergartenService blocStaffKindergartenService;
    @Resource
    private IBlocService blocService;
    @Resource
    private RemoteCommonService commonService;

    /**
     * 列表查询
     *
     * @param blocStaffQueryVO 列表查询入参
     * @return 列表
     */
    @Override
    public IPage<BlocStaffBO> listPage(BlocStaffQueryVO blocStaffQueryVO) {
        IPage<BlocStaffBO> page = new Page<>(blocStaffQueryVO.getPageNum(), blocStaffQueryVO.getPageSize());
        List<BlocStaffBO> list = baseMapper.detailsListPage(page, blocStaffQueryVO);
        list.forEach(item -> {
            if (!CollectionUtils.isEmpty(item.getPosts())) {
                item.setPosts(Arrays.stream(StringUtils.split(item.getPosts().get(Constants.ZERO), ',')).collect(Collectors.toList()));
            }
        });
        page.setRecords(list);
        return page;
    }

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(BlocStaffEditVO editVO) {
        Long blocId = SecurityUtils.getUserBlocId();
        // 1.检验岗位Id是否合法, 是否越权
        commonService.verifyPost(RoleType.BLOC, editVO.getPostIds());
        // 2.获取集团员工信息
        BlocStaff byId = super.getById(editVO.getId());
        if (byId == null) {
            throw new ServiceException(ErrorEnums.BLOC_STAFF_NOT_EXIST);
        }
        if (!Objects.equals(byId.getBlocId(), blocId) || byId.getIdentity() == UserIdentity.ADMIN) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        if (!Objects.equals(byId.getStaffNumber(), editVO.getStaffNumber())) {
            // 2.校验员工编号是否重复
            if (this.getStaffNumberExist(editVO.getStaffNumber())) {
                throw new ServiceException(ErrorEnums.STAFF_NUMBER_EXIST);
            }
        }
        // 3.判断当前手机号是否已存在用户
        if (!byId.getPhone().equals(editVO.getPhone())) {
            SysUser user = remoteSysUserService.selectByPhone(editVO.getPhone());
            if (user != null) {
                throw new ServiceException(ErrorEnums.PHONE_EXISTS);
            }
        }
        // 4.修改集团员工的信息
        BlocStaff blocStaff = new BlocStaff();
        blocStaff.initUpdateProp();
        blocStaff.setSex(UserSex.values()[editVO.getSex()]);
        BeanUtils.copyBeanProp(blocStaff, editVO);
        super.update(blocStaff,
                new UpdateWrapper<BlocStaff>().lambda()
                        .eq(BlocStaff::getId, editVO.getId())
        );
        // 5.修改登录用户信息;
        SysUser update = new SysUser();
        update.setUserId(byId.getUid());
        update.setUserName(editVO.getPhone());
        update.setNickName(editVO.getName());
        update.setAvatar(editVO.getAvatar());
        update.setPhonenumber(editVO.getPhone());
        if (editVO.getSex() != null) {
            update.setSex(UserSex.values()[editVO.getSex()]);
        }
        remoteSysUserService.updateById(update);
        // 6.删除集团员工与集团岗位关联关系
        remoteSysUserPostService.deleteUserPostByUserId(byId.getUid(), UserRoleType.BLOC_PEOPLE);
        // 7.重新添加集团员工与集团岗位的关联关系
        commonService.addUserPost(byId.getUid(), editVO.getPostIds(), UserRoleType.BLOC_PEOPLE);
    }

    /**
     * 添加集团员工
     *
     * @param addVO 添加入参
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void insert(BlocStaffAddVO addVO) {
        // 1.校验岗位和手机号
        commonService.verifyPostAndPhone(RoleType.BLOC, addVO.getPostIds(), addVO.getPhone());
        // 3.创建登录用户信息
        SysUser sysUser = SysUser.initSysUser();
        sysUser.setUserName(addVO.getPhone());
        sysUser.setNickName(addVO.getName());
        sysUser.setPhonenumber(addVO.getPhone());
        sysUser.setAvatar(addVO.getAvatar());
        if (addVO.getSex() != null) {
            sysUser.setSex(UserSex.values()[addVO.getSex()]);
        }
        remoteSysUserService.insertUser(sysUser);
        // 4.创建集团员工信息
        LoginUser login = SecurityUtils.getLoginUser();
        BlocStaff blocStaff = new BlocStaff();
        BeanUtils.copyBeanProp(blocStaff, addVO);
        blocStaff.setUid(sysUser.getUserId());
        blocStaff.setIdentity(UserIdentity.PEOPLE);
        blocStaff.setCreateTime(LocalDateTime.now());
        blocStaff.setDelFlag(Constants.FALSE);
        blocStaff.setCreateBy(login.getUserId());
        blocStaff.setBlocId(login.getUser().getBlocId());
        blocStaff.setIsQuit(Constants.FALSE);
        blocStaff.setSex(sysUser.getSex());
        this.save(blocStaff);
        // 5.将刚才创建的登录用户与校区员工关联起来
        SysUser update = new SysUser();
        update.setUserId(sysUser.getUserId());
        update.setAssociationType(AssociationType.BLOC);
        update.setAssociationId(blocStaff.getId());
        remoteSysUserService.updateById(update);
        // 6.关联校区员工与岗位的关系
        commonService.addUserPost(sysUser.getUserId(), addVO.getPostIds(), UserRoleType.BLOC_PEOPLE);
        // 7.自增集团员工数量
        blocService.incrKgCount(login.getUser().getBlocId());
    }

    /**
     * 根据Id查询集团员工
     *
     * @param id id
     * @return 详情
     */
    @Override
    public BlocStaffDetailsBO getDetailsById(Long id) {
        return this.getDetailsById(id, Constants.TRUE);
    }

    /**
     * 根据Id查询集团员工
     *
     * @param id        id
     * @param queryPost 是否查询岗位
     * @return 详情
     */
    @Override
    public BlocStaffDetailsBO getDetailsById(Long id, boolean queryPost) {
        // 1.获取集团员工的详情
        BlocStaffDetailsBO detailsBO = baseMapper.getDetailsById(id);
        if (detailsBO == null) {
            return null;
        }
        // 2.判断数据是否越权
        if (!detailsBO.getBlocId().equals(SecurityUtils.getUserBlocId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        if (queryPost) {
            // 3.获取集团员工关联的集团岗位Id
            List<SysUserRole> sysUserPosts = remoteSysUserPostService.selectUserJoinPost(detailsBO.getUid(), UserRoleType.BLOC_PEOPLE);
            detailsBO.setPostIds(sysUserPosts.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
        }
        return detailsBO;
    }

    /**
     * 根据集团ID修改集团管理员的信息
     *
     * @param kgBlocStaff 实体
     * @param blocId      集团ID
     * @return 结果
     */
    @Override
    public boolean updateAdminByBlocId(BlocStaff kgBlocStaff, Long blocId) {
        // 1.根据手机号和集团ID查询集团管理员
        BlocStaff blocStaff = super.getOne(new QueryWrapper<BlocStaff>().lambda()
                .eq(BlocStaff::getBlocId, blocId)
                .eq(BlocStaff::getIdentity, UserIdentity.ADMIN)
        );
        // 2.判断集团管理员是否存在
        if (blocStaff == null) {
            throw new ServiceException(ErrorEnums.BLOC_ADMIN_NOT_EXIST);
        }
        // 3.根据集团管理员的ID进行修改
        BlocStaff update = new BlocStaff();
        BeanUtils.copyBeanProp(update, kgBlocStaff);
        update.setId(blocStaff.getId());
        return super.updateById(update);
    }

    /**
     * 根据集团ID获取集团管理员
     *
     * @param blocId 集团ID
     * @return 集团管理员信息
     */
    @Override
    public BlocStaff selectAdminByBlocId(Long blocId) {
        return super.getOne(
                new QueryWrapper<BlocStaff>().lambda()
                        .eq(BlocStaff::getBlocId, blocId)
                        .eq(BlocStaff::getIdentity, UserIdentity.ADMIN)
        );
    }

    /**
     * 查询指定集团下的在职员工数量; 除了管理员
     *
     * @param blocId 集团Id
     * @return 除了管理员的在职员工数量
     */
    @Override
    public int selectLiveStaffCountByBlocId(Long blocId) {
        return super.count(
                new QueryWrapper<BlocStaff>().lambda()
                        .eq(BlocStaff::getBlocId, blocId)
                        .eq(BlocStaff::getIsQuit, Constants.FALSE)
                        .eq(BlocStaff::getIdentity, UserIdentity.PEOPLE)
        );
    }

    /**
     * 删除指定集团下的管理员信息
     *
     * @param blocId 集团Id
     */
    @Override
    public void deleteAdminByBlocId(Long blocId) {
        // 1.获取集团的管理员信息
        BlocStaff admin = super.getOne(new QueryWrapper<BlocStaff>().lambda()
                .eq(BlocStaff::getBlocId, blocId)
                .eq(BlocStaff::getIdentity, UserIdentity.ADMIN)
        );
        if (admin == null) {
            throw new ServiceException(ErrorEnums.BLOC_ADMIN_NOT_EXIST);
        }
        // 2.逻辑删除集团管理员
        BlocStaff update = new BlocStaff();
        update.initUpdateProp();
        super.update(update,
                new UpdateWrapper<BlocStaff>().lambda()
                        .eq(BlocStaff::getId, admin.getId())
                        .set(BlocStaff::getDelFlag, Constants.TRUE)
        );
    }

    /**
     * 集团员工离职
     *
     * @param quitVO 集团员工离职入参
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void quit(BlocStaffQuitVO quitVO) {
        // 1.查询集团员工
        BlocStaff blocStaff = this.verifyLoginUserHandleByBlocStaffId(quitVO.getId());
        // 2.将员工状态修改为离职
        BlocStaff updateStaff = new BlocStaff();
        updateStaff.setIsQuit(Constants.TRUE);
        updateStaff.setQuitDate(quitVO.getQuitDate());
        updateStaff.setQuitReason(quitVO.getQuitReason());
        updateStaff.initUpdateProp();
        updateStaff.setId(blocStaff.getId());
        super.updateById(updateStaff);
        // 3.查询集团员工可以进入的校区
        List<BlocStaffKindergarten> list = blocStaffKindergartenService.getByBlocStaffId(blocStaff.getId());
        if (!CollectionUtils.isEmpty(list)) {
            // 4.删除集团员工所有可进入的校区权限
            blocStaffKindergartenService.deleteByBlocStaffId(blocStaff.getId());
            // 5.删除集团员工在校区中已分配的岗位
            remoteSysUserPostService.deleteUserPostByUserIds(list.stream().map(BlocStaffKindergarten::getId).collect(Collectors.toList()), UserRoleType.BLOC_PEOPLE_INTO_KG);
        }
        // 6.自减集团员工数量
        blocService.decrKgCount(blocStaff.getBlocId());
        // 7.禁用集团员工对应的登录用户
        remoteSysUserService.disableById(blocStaff.getUid());
    }

    /**
     * 重置用户密码
     *
     * @param userIdVO 入参
     * @return 新密码
     */
    @Override
    public String resetPassword(UserIdVO userIdVO) {
        // 1.查询登录用户对应的集团员工信息
        BlocStaff staff = this.getByUid(userIdVO.getUserId());
        if (staff == null) {
            throw new ServiceException(ErrorEnums.BLOC_STAFF_NOT_EXIST);
        }
        // 2.判断集团id是否一致
        SysUser login = SecurityUtils.getUser();
        if (!Objects.equals(login.getBlocId(), staff.getBlocId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        // 3.修改用户密码
        SysUser update = new SysUser();
        update.setUserId(userIdVO.getUserId());
        String pwd = RandomUtil.randomString(Constants.PASSWORD_MIN_LENGTH);
        update.setPassword(SecurityUtils.encryptPassword(pwd));
        update.setUpdateBy(login.getUserId().toString());
        update.setUpdateTime(DateUtils.getNowDate());
        remoteSysUserService.updateById(update);
        return pwd;
    }

    /**
     * 根据登录用户Id查询集团员工
     *
     * @param userId 登录用户id
     * @return 结果
     */
    @Override
    public BlocStaff selectByUserId(Long userId) {
        return super.getOne(
                new QueryWrapper<BlocStaff>().lambda()
                        .eq(BlocStaff::getUid, userId)
        );
    }

    /**
     * 删除集团员工
     *
     * @param idVO id入参
     */
    @Override
    public void deleteById(BlocStaffIdVO idVO) {
        // 1.查询集团员工
        BlocStaff blocStaff = this.verifyLoginUserHandleByBlocStaffId(idVO.getId());
        // 2.员工未离职, 不可删除
        if (!blocStaff.getIsQuit()) {
            throw new ServiceException(ErrorEnums.BLOC_STAFF_NOT_QUIT_UNDELETABLE);
        }
        // 3.逻辑删除集团员工
        BlocStaff update = new BlocStaff();
        update.initUpdateProp();
        super.update(update,
                new UpdateWrapper<BlocStaff>().lambda()
                        .eq(BlocStaff::getId, blocStaff.getId())
                        .set(BlocStaff::getDelFlag, Constants.TRUE)
        );
        // 4.逻辑删除登录用户信息
        remoteSysUserService.deleteById(blocStaff.getUid());
    }

    /**
     * 集团员工重新入参
     *
     * @param idVO id入参
     */
    @Override
    public void entry(BlocStaffIdVO idVO) {
        // 1.获取员工并校验当前登录用户能否操作这个员工
        BlocStaff staff = this.verifyLoginUserHandleByBlocStaffId(idVO.getId());
        // 2.将员工状态修改为在职
        BlocStaff update = new BlocStaff();
        update.setIsQuit(Constants.FALSE);
        update.initUpdateProp();
        update.setId(staff.getId());
        super.updateById(update);
        // 3.自增集团员工数量
        blocService.incrKgCount(staff.getBlocId());
        // 4.启用校区员工对应的登录用户
        remoteSysUserService.enableById(staff.getUid());
    }

    /**
     * 根据登录用户id查询集团员工
     *
     * @param userId 登录用户id
     * @return 结果
     */
    @Override
    public BlocStaff getByUid(Long userId) {
        return super.getOne(
                new QueryWrapper<BlocStaff>().lambda()
                        .eq(BlocStaff::getUid, userId)
        );
    }

    /**
     * 校验登录用户能否操作指定的集团员工
     *
     * @param blocStaffId 被操作的集团员工Id
     */
    public BlocStaff verifyLoginUserHandleByBlocStaffId(Long blocStaffId) {
        // 1.查询集团员工
        BlocStaff staff = super.getOne(
                new QueryWrapper<BlocStaff>().lambda()
                        .eq(BlocStaff::getId, blocStaffId)
        );
        // 2.判断集团用户是否存在
        if (staff == null) {
            throw new ServiceException(ErrorEnums.BLOC_STAFF_NOT_EXIST);
        }
        // 3.判断集团Id是否一致
        Long blocId = SecurityUtils.getUserBlocId();
        if (!Objects.equals(staff.getBlocId(), blocId)) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        // 3.操作的是集团管理员抛出异常
        if (staff.getIdentity().equals(UserIdentity.ADMIN)) {
            throw new ServiceException(ErrorEnums.DELETE_FAIL);
        }
        return staff;
    }

    /**
     * 获取员工编号是否存在
     *
     * @param staffNumber 员工编号
     * @return 结果
     */
    public boolean getStaffNumberExist(String staffNumber) {
        int count = super.count(
                new QueryWrapper<BlocStaff>().lambda()
                        .eq(BlocStaff::getStaffNumber, staffNumber)
                        .eq(BlocStaff::getBlocId, SecurityUtils.getUserBlocId())
        );
        return count > Constants.ZERO;
    }
}
