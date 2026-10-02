package com.zhenshu.system.business.bloc.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.enums.base.UserRoleType;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.system.business.bloc.base.domain.po.BlocStaff;
import com.zhenshu.system.business.bloc.base.domain.po.BlocStaffKindergarten;
import com.zhenshu.system.business.bloc.base.domain.po.Kindergarten;
import com.zhenshu.system.business.bloc.base.mapper.BlocStaffKindergartenMapper;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffKindergartenService;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffService;
import com.zhenshu.system.business.bloc.base.service.IKindergartenService;
import com.zhenshu.system.remote.ruoyi.RemoteSysUserPostService;
import com.zhenshu.system.remote.ruoyi.RemoteSysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:58
 * @desc serviceImpl
 */
@Slf4j
@Service
public class BlocStaffKindergartenServiceImpl extends ServiceImpl<BlocStaffKindergartenMapper, BlocStaffKindergarten> implements IBlocStaffKindergartenService {
    @Resource
    private IKindergartenService kindergartenService;
    @Resource
    private IBlocStaffService blocStaffService;
    @Resource
    private RemoteSysUserPostService remoteSysUserPostService;
    @Resource
    private RemoteSysUserService remoteSysUserService;

                /**
     * 检查用户是否能进入校区
     *
     * @param userId 用户id
     * @param kgId   校区id
     * @return
     */
    @Override
    public boolean checkUserToKg(Long userId, Long kgId) {
        // 1.获取登录用户Id对应的员工信息
        BlocStaff blocStaff = blocStaffService.selectByUserId(userId);
        // 2.判断用户是否能进入校区
        int count = super.count(
                new QueryWrapper<BlocStaffKindergarten>().lambda()
                        .eq(BlocStaffKindergarten::getKindergartenId, kgId)
                        .eq(BlocStaffKindergarten::getBlocStaffId, blocStaff.getId())
        );
        return count > Constants.ZERO;
    }

    /**
     * 根据集团人员id查询集团人员进入校区的权限信息
     *
     * @param blocStaffId 集团人员id
     * @return 结果
     */
    @Override
    public List<BlocStaffKindergarten> getByBlocStaffId(Long blocStaffId) {
        return super.list(
                new QueryWrapper<BlocStaffKindergarten>().lambda()
                        .eq(BlocStaffKindergarten::getBlocStaffId, blocStaffId)
        );
    }

    /**
     * 根据集团员工id和校区id获取数据
     *
     * @param staffId 集团员工id
     * @param kgId    校区id
     * @return 结果
     */
    @Override
    public BlocStaffKindergarten getByBlocStaffIdAndKgId(Long staffId, Long kgId) {
        return super.getOne(
                new QueryWrapper<BlocStaffKindergarten>().lambda()
                        .eq(BlocStaffKindergarten::getBlocStaffId, staffId)
                        .eq(BlocStaffKindergarten::getKindergartenId, kgId)
        );
    }

    /**
     * 删除指定集团员工可进入的校区权限
     *
     * @param staffId 集团员工
     */
    @Override
    public void deleteByBlocStaffId(Long staffId) {
        BlocStaffKindergarten update = new BlocStaffKindergarten();
        update.initUpdateProp();
        super.update(update,
                new UpdateWrapper<BlocStaffKindergarten>().lambda()
                        .eq(BlocStaffKindergarten::getBlocStaffId, staffId)
                        .set(BlocStaffKindergarten::getDelFlag, Constants.TRUE)
        );
    }

    /**
     * 获取指定集团登录用户, 并校验当前用户是否能获取这个用户
     *
     * @param userId userId
     * @return userId对应的登录用户
     */
    public SysUser getAndVerifyBlocUserByUserId(Long userId) {
        // 1.查询登录用户对应的校区员工信息
        BlocStaff staff = blocStaffService.getByUid(userId);
        if (staff == null) {
            throw new ServiceException(ErrorEnums.BLOC_STAFF_NOT_EXIST);
        }
        // 2.判断集团id是否一致
        SysUser login = SecurityUtils.getUser();
        if (!Objects.equals(login.getBlocId(), staff.getBlocId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        // 3.查询登录用户
        SysUser user = remoteSysUserService.selectById(userId);
        if (user == null) {
            throw new ServiceException(ErrorEnums.SYS_USER_NOT_EXIST);
        }
        return user;
    }

}
