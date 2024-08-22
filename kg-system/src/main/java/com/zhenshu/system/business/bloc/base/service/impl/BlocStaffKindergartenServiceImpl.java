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
import com.zhenshu.system.business.bloc.base.domain.bo.BlocStaffKindergartenDetailsBO;
import com.zhenshu.system.business.bloc.base.domain.po.BlocStaff;
import com.zhenshu.system.business.bloc.base.domain.po.BlocStaffKindergarten;
import com.zhenshu.system.business.bloc.base.domain.po.Kindergarten;
import com.zhenshu.system.business.bloc.base.domain.vo.BlocStaffKindergartenEditVO;
import com.zhenshu.system.business.bloc.base.mapper.BlocStaffKindergartenMapper;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffKindergartenService;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffService;
import com.zhenshu.system.business.bloc.base.service.IKindergartenService;
import com.zhenshu.system.business.bloc.tokg.domain.bo.GoToKindergartenBO;
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
     * 根据Id查询
     *
     * @param userId 登录用户Id
     * @return 结果
     */
    @Override
    public BlocStaffKindergartenDetailsBO getDetailsById(Long userId) {
        // 1.获取登录用户
        SysUser loginUser = SecurityUtils.getUser();
        SysUser user = this.getAndVerifyBlocUserByUserId(userId);
        // 2.获取当前登录用户所属集团下的所有校区
        List<Kindergarten> kgList = kindergartenService.getListByBlocId(loginUser.getBlocId());
        // 3.查询集团员工可进入的校区权限
        List<BlocStaffKindergarten> list = super.list(
                new QueryWrapper<BlocStaffKindergarten>().lambda()
                        .eq(BlocStaffKindergarten::getBlocStaffId, user.getAssociationId())
        );
        Set<Long> kgIdSet = list.stream().map(BlocStaffKindergarten::getKindergartenId).collect(Collectors.toSet());
        // 4.封装返回值
        BlocStaffKindergartenDetailsBO detailsBO = new BlocStaffKindergartenDetailsBO();
        detailsBO.setBlocStaffName(user.getNickName());
        detailsBO.setKgList(kgList.stream().map(item -> {
            GoToKindergartenBO bo = new GoToKindergartenBO();
            bo.setKgId(item.getId());
            bo.setName(item.getKindergartenName());
            bo.setAddress(item.getKindergartenAddress());
            bo.setIsChecked(kgIdSet.contains(item.getId()));
            return bo;
        }).collect(Collectors.toList()));
        return detailsBO;
    }

    /**
     * 修改集团员工进入校区的权限
     *
     * @param editVO 入参
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGotoKg(BlocStaffKindergartenEditVO editVO) {
        // 1.校验登录用户是否能操作入参userId对应的登录用户
        SysUser loginUser = SecurityUtils.getUser();
        SysUser user = this.getAndVerifyBlocUserByUserId(editVO.getUserId());
        // 2.校验传入的校区是否合法
        if (!CollectionUtils.isEmpty(editVO.getKgIds()) && !kindergartenService.verifyKgIds(editVO.getKgIds())) {
            throw new ServiceException(ErrorEnums.KG_ID_ILLEGALITY);
        }
        // 3.查询集团员工可以进入的校区
        List<BlocStaffKindergarten> list = super.list(
                new QueryWrapper<BlocStaffKindergarten>().lambda()
                        .eq(BlocStaffKindergarten::getBlocStaffId, user.getAssociationId())
        );
        // 4.将需要删除的和新增的数据进行分类
        // deleteList保存BlocStaffKindergarten表的id
        List<Long> deleteList = new ArrayList<>(list.size());
        for (BlocStaffKindergarten blocStaffKindergarten : list) {
            if (CollectionUtils.isEmpty(editVO.getKgIds()) || !editVO.getKgIds().contains(blocStaffKindergarten.getKindergartenId())) {
                deleteList.add(blocStaffKindergarten.getId());
            }
        }
        // 集团人员之前可进入的校区id集合
        Set<Long> toKgSet = list.stream().map(BlocStaffKindergarten::getKindergartenId).collect(Collectors.toSet());
        // insertList保存需要新增的校区id
        List<Long> insertList = new ArrayList<>(list.size());
        if(!CollectionUtils.isEmpty(editVO.getKgIds())){
            for (Long kgId : editVO.getKgIds()) {
                if (CollectionUtils.isEmpty(toKgSet) || !toKgSet.contains(kgId)) {
                    insertList.add(kgId);
                }
            }
        }
        if (!CollectionUtils.isEmpty(deleteList)) {
            // 5.删除修改后集团员工不可进入的校区权限
            BlocStaffKindergarten update = new BlocStaffKindergarten();
            update.initUpdateProp();
            super.update(update,
                    new UpdateWrapper<BlocStaffKindergarten>().lambda()
                            .in(BlocStaffKindergarten::getId, deleteList)
                            .set(BlocStaffKindergarten::getDelFlag, Constants.TRUE)
            );
            // 6.删除修改后集团员工不可进入的校区的权限
            remoteSysUserPostService.deleteUserPostByUserIds(deleteList, UserRoleType.BLOC_PEOPLE_INTO_KG);
        }
        // 7.如果没有需要新增可进入的校区, 结束方法
        if (CollectionUtils.isEmpty(insertList)) {
            return;
        }
        // 8.添加集团员工可进入的校区权限
        List<BlocStaffKindergarten> saveList = new ArrayList<>();
        for (Long kgId : insertList) {
            BlocStaffKindergarten data = new BlocStaffKindergarten();
            data.initCreateProp();
            data.setBlocId(loginUser.getBlocId());
            data.setBlocStaffId(user.getAssociationId());
            data.setKindergartenId(kgId);
            saveList.add(data);
        }
        super.saveBatch(saveList);
    }

    /**
     * 获取登录用户可进入的校区
     *
     * @return 结果
     */
    @Override
    public List<GoToKindergartenBO> getUserGotoKgList() {
        SysUser login = SecurityUtils.getUser();
        List<Kindergarten> kgList = new ArrayList<>(Constants.ZERO);
        if (login.getIdentity() == LoginIdentity.BLOC_PEOPLE) {
            // 1.查询登录用户可进入的所有校区
            List<BlocStaffKindergarten> list = super.list(
                    new QueryWrapper<BlocStaffKindergarten>().lambda()
                            .eq(BlocStaffKindergarten::getBlocStaffId, login.getAssociationId())
            );
            if (CollectionUtils.isEmpty(list)) {
                return new ArrayList<>(Constants.ZERO);
            }
            // 2.根据校区ID查询校区信息
            kgList = kindergartenService.getByIds(list.stream().map(BlocStaffKindergarten::getKindergartenId).collect(Collectors.toList()));
        } else if (login.getIdentity() == LoginIdentity.BLOC_ADMIN) {
            // 集团管理员可以进入所有校区
            kgList = kindergartenService.getListByBlocId(login.getBlocId());
        }
        // 3.封装返回值
        return kgList.stream().map(item -> {
            GoToKindergartenBO bo = new GoToKindergartenBO();
            bo.setKgId(item.getId());
            bo.setAddress(item.getKindergartenAddress());
            bo.setName(item.getKindergartenName());
            return bo;
        }).collect(Collectors.toList());
    }

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
