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
import com.zhenshu.system.business.bloc.base.domain.vo.KindergartenQueryVO;
import com.zhenshu.system.business.bloc.base.mapper.KindergartenMapper;
import com.zhenshu.system.business.bloc.base.service.IKindergartenService;
import com.zhenshu.system.business.kg.base.record.domain.po.KindergartenStaff;
import com.zhenshu.system.business.kg.base.record.domain.vo.KindergartenResetPasswordVO;
import com.zhenshu.system.business.kg.base.record.service.IKindergartenStaffService;
import com.zhenshu.system.business.ruoyi.domain.bo.PostRoleNameBO;
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
    private RemoteSysUserService remoteSysUserService;
    private IKindergartenStaffService kindergartenStaffService;
    private RemoteSchoolSurveyService remoteSchoolSurveyService;

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
