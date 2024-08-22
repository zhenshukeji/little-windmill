package com.zhenshu.system.business.bloc.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.enums.base.AssociationType;
import com.zhenshu.common.enums.base.UserIdentity;
import com.zhenshu.common.enums.system.UserSex;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.bean.BeanUtils;
import com.zhenshu.system.business.bloc.base.domain.bo.KindergartenBO;
import com.zhenshu.system.business.bloc.base.domain.bo.KindergartenDetailsBO;
import com.zhenshu.system.business.bloc.base.domain.bo.NameInfoBO;
import com.zhenshu.system.business.bloc.base.domain.po.Kindergarten;
import com.zhenshu.system.business.bloc.base.domain.vo.KindergartenAddVO;
import com.zhenshu.system.business.bloc.base.domain.vo.KindergartenDeleteVO;
import com.zhenshu.system.business.bloc.base.domain.vo.KindergartenEditVO;
import com.zhenshu.system.business.bloc.base.domain.vo.KindergartenQueryVO;
import com.zhenshu.system.business.bloc.base.mapper.KindergartenMapper;
import com.zhenshu.system.business.bloc.base.service.IKindergartenService;
import com.zhenshu.system.business.bloc.finance.domain.bo.KindergartenSimpleBO;
import com.zhenshu.system.business.kg.base.record.domain.po.KindergartenStaff;
import com.zhenshu.system.business.kg.base.record.domain.vo.KindergartenResetPasswordVO;
import com.zhenshu.system.business.kg.base.record.service.IKindergartenStaffService;
import com.zhenshu.system.business.platform.domain.po.Bloc;
import com.zhenshu.system.business.platform.service.IBlocService;
import com.zhenshu.system.business.ruoyi.domain.bo.PostRoleNameBO;
import com.zhenshu.system.remote.kg.base.advanced.RemoteAttendanceTimeService;
import com.zhenshu.system.remote.kg.base.advanced.RemoteSchoolSurveyService;
import com.zhenshu.system.remote.ruoyi.RemoteSysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:58
 * @desc
 */
@Service
public class KindergartenServiceImpl extends ServiceImpl<KindergartenMapper, Kindergarten> implements IKindergartenService {
    @Resource
    private RemoteSysUserService remoteSysUserService;
    @Resource
    private IKindergartenStaffService kindergartenStaffService;
    @Resource
    private IBlocService blocService;
    @Resource
    private RemoteSchoolSurveyService remoteSchoolSurveyService;
    @Resource
    private RemoteAttendanceTimeService remoteAttendanceTimeService;


    /**
     * 列表查询
     *
     * @param queryVO 列表查询入参
     * @return 结果
     */
    @Override
    public IPage<KindergartenBO> listPage(KindergartenQueryVO queryVO) {
        IPage<KindergartenBO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return baseMapper.listPage(page, queryVO);
    }

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(KindergartenEditVO editVO) {
        Long blocId = SecurityUtils.getUserBlocId();
        // 2.查询当前校区管理员的信息
        KindergartenStaff kgAdmin = kindergartenStaffService.selectAdminByBlocId(editVO.getId());
        if (kgAdmin == null) {
            // 校区管理员不存在代表校区也不存在
            throw new ServiceException(ErrorEnums.KG_ONT_EXIST);
        } else if (!kgAdmin.getBlocId().equals(blocId)) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        // 3.判断手机号是否重复
        if (!kgAdmin.getPhone().equals(editVO.getPrincipalPhone())) {
            SysUser sysUser = remoteSysUserService.selectByPhone(editVO.getPrincipalPhone());
            if (sysUser != null) {
                throw new ServiceException(ErrorEnums.PHONE_EXISTS);
            }
        }
        // 4.修改校区信息
        Kindergarten kindergarten = new Kindergarten();
        BeanUtils.copyBeanProp(kindergarten, editVO);
        kindergarten.setBlocId(null);
        super.update(kindergarten,
                new UpdateWrapper<Kindergarten>().lambda()
                        .eq(Kindergarten::getId, editVO.getId())
                        .set(Kindergarten::getUpdateTime, LocalDateTime.now())
                        .set(Kindergarten::getUpdateBy, SecurityUtils.getUserId())
        );
        // 5.判断修改之前校区管理员手机号、姓名与修改后的手机号、姓名是否有改变
        boolean phoneEquals = kgAdmin.getPhone().equals(editVO.getPrincipalPhone());
        boolean nameEquals = kgAdmin.getName().equals(editVO.getPrincipalName());
        // 如果都一致结束方法
        if (phoneEquals && nameEquals) {
            return;
        }
        // 6.修改校区人员表
        KindergartenStaff staff = new KindergartenStaff();
        staff.setId(kgAdmin.getId());
        staff.setName(editVO.getPrincipalName());
        staff.setPhone(editVO.getPrincipalPhone());
        kindergartenStaffService.updateById(staff);
        // 7.修改登录用户表
        remoteSysUserService.updatePhoneByAssociationId(editVO.getPrincipalName(), editVO.getPrincipalPhone(), kgAdmin.getId(), AssociationType.KG);
    }

    /**
     * 添加
     *
     * @param kindergartenAddVO 入参
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(KindergartenAddVO kindergartenAddVO) {
        // 1.根据手机号码查询登录用户是否存在
        SysUser sysUser = remoteSysUserService.selectByPhone(kindergartenAddVO.getPrincipalPhone());
        // 2.如果查询到数据, 代表手机号重复, 抛出异常
        if (sysUser != null) {
            throw new ServiceException(ErrorEnums.PHONE_EXISTS);
        }
        // 3.添加校区
        Kindergarten kindergarten = new Kindergarten();
        BeanUtils.copyBeanProp(kindergarten, kindergartenAddVO);
        kindergarten.setCreateTime(LocalDateTime.now());
        kindergarten.setDelFlag(Constants.FALSE);
        kindergarten.setCreateBy(SecurityUtils.getUserId());
        kindergarten.setBlocId(SecurityUtils.getUserBlocId());
        kindergarten.setBlocId(SecurityUtils.getUserBlocId());
        super.save(kindergarten);
        // 4.创建登录用户; 新创建的登录用户还没有关联任何的人员
        sysUser = SysUser.initSysUser();
        sysUser.setUserName(kindergartenAddVO.getPrincipalPhone());
        sysUser.setNickName(kindergartenAddVO.getPrincipalName());
        sysUser.setPhonenumber(kindergartenAddVO.getPrincipalPhone());
        sysUser.setSex(UserSex.UNKNOWN);
        sysUser.setPassword(SecurityUtils.encryptPassword(kindergartenAddVO.getPassword()));
        remoteSysUserService.insertUser(sysUser);
        // 5.创建校区员工, 员工类型为校区管理员
        KindergartenStaff staff = new KindergartenStaff();
        staff.setUid(sysUser.getUserId());
        staff.setKgId(kindergarten.getId());
        staff.setBlocId(SecurityUtils.getUserBlocId());
        staff.setName(kindergartenAddVO.getPrincipalName());
        staff.setPhone(kindergartenAddVO.getPrincipalPhone());
        staff.setIdentity(UserIdentity.ADMIN);
        staff.setIsQuit(Constants.FALSE);
        staff.setCreateBy(SecurityUtils.getUserId());
        staff.setCreateTime(LocalDateTime.now());
        staff.setDelFlag(Constants.FALSE);
        kindergartenStaffService.save(staff);
        // 6.将刚才创建的登录用户与校区员工关联起来
        SysUser update = new SysUser();
        update.setUserId(sysUser.getUserId());
        update.setAssociationType(AssociationType.KG);
        update.setAssociationId(staff.getId());
        remoteSysUserService.updateById(update);
        // 7.修改操作用户所在集团的校区数量
        blocService.update(
                new UpdateWrapper<Bloc>().lambda()
                        .eq(Bloc::getId, staff.getBlocId())
                        .setSql(Constants.INCR_BLOC_KG_COUNT)
        );
        // 8.新增默认校区概况
        remoteSchoolSurveyService.initSchoolSurvey(kindergarten.getId());
        // 9.添加默认的考勤时间
        remoteAttendanceTimeService.initAttendanceTime(kindergarten.getId());
    }

    /**
     * 删除
     *
     * @param deleteVO 删除入参
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(KindergartenDeleteVO deleteVO) {
        // 1.查询校区是否存在
        Kindergarten kindergarten = super.getById(deleteVO.getId());
        if (kindergarten == null) {
            throw new ServiceException(ErrorEnums.KG_ONT_EXIST);
        }
        // 2.判断集团Id是否一致
        Long blocId = SecurityUtils.getUserBlocId();
        if (!kindergarten.getBlocId().equals(blocId)) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        // 3.判断校区下是否存在员工
        if (kindergarten.getStaffCount() > Constants.ZERO) {
            throw new ServiceException(ErrorEnums.KG_HAS_STAFF);
        }
        // 4.判断校区下是否存未离校的学员
        if (kindergarten.getStudentCount() > Constants.ZERO) {
            throw new ServiceException(ErrorEnums.KG_HAS_STUDENT);
        }
        // 5.逻辑删除校区
        super.update(
                new UpdateWrapper<Kindergarten>().lambda()
                        .eq(Kindergarten::getBlocId, blocId)
                        .eq(Kindergarten::getId, deleteVO.getId())
                        .set(Kindergarten::getUpdateTime, LocalDateTime.now())
                        .set(Kindergarten::getUpdateBy, SecurityUtils.getUserId())
                        .set(Kindergarten::getDelFlag, Constants.TRUE)
        );
        // 6.逻辑删除校区管理员的员工信息
        kindergartenStaffService.deleteAdminByKgId(deleteVO.getId());
        // 7.逻辑删除校区管理员的登录用户信息
        remoteSysUserService.deleteByPhone(kindergarten.getPrincipalPhone());
        // 8.修改操作用户所在集团的校区数量
        blocService.incrKgCount(blocId);
    }

    /**
     * 根据Id查询
     *
     * @param id id
     * @return 结果
     */
    @Override
    public KindergartenDetailsBO getDetailsById(Long id) {
        Kindergarten kindergarten = this.getById(id);
        if (kindergarten != null && !kindergarten.getBlocId().equals(SecurityUtils.getUserBlocId())) {
            throw new ServiceException(ErrorEnums.BIZ_ERROR_CODE);
        }
        KindergartenDetailsBO kindergartenDetailsBO = new KindergartenDetailsBO();
        BeanUtils.copyBeanProp(kindergartenDetailsBO, kindergarten);
        return kindergartenDetailsBO;
    }

    /**
     * 查询指定集团下的校区数量
     *
     * @param blocId 集团Id
     * @return 校区数量
     */
    @Override
    public int selectKgCountByBlocId(Long blocId) {
        return super.count(new QueryWrapper<Kindergarten>().lambda().eq(Kindergarten::getBlocId, blocId));
    }

    /**
     * 重置密码
     *
     * @param restPwdVO 重置密码入参
     * @return 结果
     */
    @Override
    public void resetPassword(KindergartenResetPasswordVO restPwdVO) {
        Long blocId = SecurityUtils.getUserBlocId();
        // 1.查询校区信息
        Kindergarten kindergarten = super.getById(restPwdVO.getId());
        if (kindergarten == null) {
            throw new ServiceException(ErrorEnums.KG_ONT_EXIST);
        }
        // 2.判断集团Id是否一致
        if (!kindergarten.getBlocId().equals(blocId)) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        // 3.修改校区管理员的密码
        remoteSysUserService.resetPwd(kindergarten.getPrincipalPhone(), restPwdVO.getPassword());
    }

    /**
     * 获取指定集团下的所有校区
     *
     * @param blocId 集团Id
     * @return 结果
     */
    @Override
    public List<Kindergarten> getListByBlocId(Long blocId) {
        return super.list(
                new QueryWrapper<Kindergarten>().lambda()
                        .eq(Kindergarten::getBlocId, blocId)
        );
    }

    /**
     * 校验校区Id是否合法
     *
     * @param kgIds 校区id集合
     * @return 结果
     */
    @Override
    public boolean verifyKgIds(Collection<Long> kgIds) {
        int count = super.count(
                new QueryWrapper<Kindergarten>().lambda()
                        .in(Kindergarten::getId, kgIds)
                        .eq(Kindergarten::getBlocId, SecurityUtils.getUserBlocId())
        );
        return count == kgIds.size();
    }

    /**
     * 批量查询校区
     *
     * @param ids 校区Id结婚
     * @return 结果
     */
    @Override
    public List<Kindergarten> getByIds(List<Long> ids) {
        return super.list(
                new QueryWrapper<Kindergarten>().lambda()
                        .in(Kindergarten::getId, ids)
        );
    }

    /**
     * 自增学校员工数量
     *
     * @param kgId 学校id
     */
    @Override
    public void incrStaffCount(Long kgId) {
        super.update(
                new UpdateWrapper<Kindergarten>().lambda()
                        .eq(Kindergarten::getId, kgId)
                        .setSql(Constants.INCR_KG_STAFF_COUNT)
        );
    }

    /**
     * 自减学校员工数量
     *
     * @param kgId 学校id
     */
    @Override
    public void decrStaffCount(Long kgId) {
        super.update(
                new UpdateWrapper<Kindergarten>().lambda()
                        .eq(Kindergarten::getId, kgId)
                        .setSql(Constants.DECR_KG_STAFF_COUNT)
        );
    }

    @Override
    public NameInfoBO getKgNameAndBlocName(Long kgId) {
        return this.baseMapper.getKgNameAndBlocName(kgId);
    }

    /**
     * 自增学校学生数量
     *
     * @param kgId 学校id
     */
    @Override
    public void incrStudentCount(Long kgId) {
        super.update(
                new UpdateWrapper<Kindergarten>().lambda()
                        .eq(Kindergarten::getId, kgId)
                        .setSql(Constants.INCR_KG_STUDENT_COUNT)
        );
    }

    /**
     * 自增学校学生数量
     *
     * @param kgId 学校id
     */
    @Override
    public void incrStaffCount(Long kgId, int size) {
        super.update(
                new UpdateWrapper<Kindergarten>().lambda()
                        .eq(Kindergarten::getId, kgId)
                        .setSql(String.format(Constants.INCR_BY_KG_STUDENT_COUNT, size))
        );
    }

    /**
     * 自减学校学生数量
     *
     * @param kgId 学校id
     */
    @Override
    public void decrStudentCount(Long kgId) {
        super.update(
                new UpdateWrapper<Kindergarten>().lambda()
                        .eq(Kindergarten::getId, kgId)
                        .setSql(Constants.DECR_KG_STUDENT_COUNT)
        );
    }

    /**
     * 获取指定集团下的所有校区
     *
     * @param blocId 集团id
     * @return 结果
     */
    @Override
    public List<KindergartenSimpleBO> getListAllByBlocId(Long blocId) {
        return super.list(
                new QueryWrapper<Kindergarten>().lambda()
                        .eq(Kindergarten::getBlocId, blocId)
        ).stream().map(item -> {
            KindergartenSimpleBO bo = new KindergartenSimpleBO();
            bo.setId(item.getId());
            bo.setName(item.getKindergartenName());
            return bo;
        }).collect(Collectors.toList());
    }

    /**
     * 通过校区id获取管理员uid和岗位名称
     *
     * @param kgId 校区id
     * @return 结果
     */
    @Override
    public PostRoleNameBO getUidByKgId(Long kgId) {
        return baseMapper.getUid(kgId);
    }

    /**
     * 获取园区管理员uid
     *
     * @param kgId 校区id集合
     * @return 结果
     */
    @Override
    public List<Long> getUidByKgId(List<Long> kgId) {
        return baseMapper.getIds(kgId);
    }

}
