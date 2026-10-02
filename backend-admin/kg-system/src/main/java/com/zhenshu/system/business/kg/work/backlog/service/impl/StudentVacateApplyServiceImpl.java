package com.zhenshu.system.business.kg.work.backlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.enums.kg.work.backlog.ApplyStatus;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.DateUtils;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomPartBO;
import com.zhenshu.system.business.kg.base.advanced.service.IClassroomService;
import com.zhenshu.system.business.kg.base.record.domain.po.Guardian;
import com.zhenshu.system.business.kg.home.domain.bo.KgHomePendingItemBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyCheckingBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyDetailsBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyReportBO;
import com.zhenshu.system.business.kg.work.backlog.domain.po.StudentVacateApply;
import com.zhenshu.system.business.kg.work.backlog.domain.vo.*;
import com.zhenshu.system.business.kg.work.backlog.mapper.StudentVacateApplyMapper;
import com.zhenshu.system.business.kg.work.backlog.service.IStudentVacateApplyService;
import com.zhenshu.system.remote.kg.base.record.RemoteGuardianService;
import com.zhenshu.system.remote.ruoyi.RemoteSysUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-12
 * @desc serviceImpl
 */
@Service
public class StudentVacateApplyServiceImpl extends ServiceImpl<StudentVacateApplyMapper, StudentVacateApply> implements IStudentVacateApplyService {
    @Resource
    private IClassroomService classroomService;
    @Resource
    private RemoteSysUserService remoteSysUserService;
    @Resource
    private RemoteGuardianService remoteGuardianService;

    /**
     * 主次列表查询
     *
     * @param queryVO 列表查询入参
     * @return 结果
     */
    @Override
    public IPage<StudentVacateApplyBO> listPendingPage(StudentVacateApplyQueryVO queryVO) {
        SysUser login = SecurityUtils.getUser();
        // 1.判断登录用户是否为教师
        if (login.getIdentity() != LoginIdentity.KG_ADMIN) {
            ClassroomPartBO partBO = classroomService.getPartByUser(login.getAssociationId(), login.getKgId());
            if (partBO != null) {
                queryVO.setClassroomId(partBO.getId());
            }
        }
        // 2.分页查询
        IPage<StudentVacateApplyBO> page = queryVO.page();
        List<StudentVacateApplyBO> list = baseMapper.listPendingPage(page, queryVO);
        page.setRecords(list);
        return page;
    }

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    @Override
    public void updateById(StudentVacateApplyEditVO editVO) {
        // 1.获取并校验id对应的数据
        StudentVacateApply data = this.getAndVerifyStudentVacateApply(editVO.getId());
        SysUser login = SecurityUtils.getUser();
        // 2.判断是否为教师, 教师需要判断班级id是否一致
        if (login.getIdentity() != LoginIdentity.KG_ADMIN) {
            ClassroomPartBO partBO = classroomService.getPartByUser(login.getAssociationId(), login.getKgId());
            if (partBO != null && !Objects.equals(data.getClassId(), partBO.getId())) {
                throw new ServiceException(ErrorEnums.CANNOT_HANDLER_OTHER_CLASS_DATA);
            }
        }
        // 3.根据id修改
        StudentVacateApply update = new StudentVacateApply();
        update.initUpdateProp();
        update.setStatus(editVO.getResult() ? ApplyStatus.CONSENT : ApplyStatus.REFUSE);
        update.setApproveOpinion(editVO.getApproveOpinion());
        update.setApproveTime(update.getUpdateTime());
        update.setApproveBy(update.getUpdateBy());
        super.update(update,
                new UpdateWrapper<StudentVacateApply>().lambda()
                        .eq(StudentVacateApply::getId, editVO.getId())
        );
    }

    /**
     * 根据Id查询
     *
     * @param id id
     * @return 结果
     */
    @Override
    public StudentVacateApplyDetailsBO getDetailsById(Long id) {
        // 1.获取信息
        StudentVacateApplyDetailsBO bo = baseMapper.getDetailsById(id);
        // 2.判空
        if (bo == null) {
            throw new ServiceException(ErrorEnums.NOT_EXIST_DATA);
        }
        // 3.判断校区id是否一致
        if (!bo.getKgId().equals(SecurityUtils.getUserKgId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        // 4.设置审批人（审批人账号可能已被删除，approve_by 查不到时必须判空，否则 NPE）
        if (Objects.equals(bo.getStatus(), ApplyStatus.CONSENT) || Objects.equals(bo.getStatus(), ApplyStatus.REFUSE)) {
            SysUser sysUser = remoteSysUserService.getById(bo.getApproveBy());
            if (sysUser != null) {
                bo.setApproveName(sysUser.getNickName());
            }
        }
        // 5.设置申请人
        Guardian guardian = remoteGuardianService.getByStudentIdAndPhone(bo.getStudentId(), bo.getApplyPhone());
        if (guardian != null) {
            bo.setApplyName(guardian.getName());
            bo.setAge(DateUtils.getAge(bo.getBirthdate(), LocalDate.now()));
        }
        return bo;
    }


    /**
     * 获取并校验当前用户是否能查看和操作id对应的数据
     *
     * @param id id
     * @return 结果
     */
    @Override
    public StudentVacateApply getAndVerifyStudentVacateApply(Long id) {
        // 1.根据id查询
        StudentVacateApply data = super.getById(id);
        // 2.判断是否有数据
        if (data == null) {
            throw new ServiceException(ErrorEnums.UPDATE_FAIL);
        }
        // 3.判断当前用户是否能查看或操作id对应的数据
        if (!Objects.equals(data.getKgId(), SecurityUtils.getUserKgId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        return data;
    }

    /**
     * 获取指定日期中指定学生的已同意的请假记录
     *
     * @param queryVO 入参
     * @return 结果
     */
    @Override
    public List<StudentVacateApplyCheckingBO> getConsentByStudentIdAndDate(StudentVacateApplyDateQueryVO queryVO) {
        return baseMapper.getByStudentIdAndDate(queryVO);
    }

    /**
     * 根据时间范围查询请假申请
     *
     * @param queryVO 入参
     * @return 结果
     */
    @Override
    public List<StudentVacateApplyCheckingBO> getConsentByStudentIdAndDateRange(StudentVacateApplyDateRangeQueryVO queryVO) {
        return baseMapper.getByStudentIdAndDateRange(queryVO);
    }

    /**
     * 列表查询申请历史
     *
     * @param queryVO 入参
     * @return 结果
     */
    @Override
    public IPage<StudentVacateApplyBO> listHistoryPage(StudentVacateApplyQueryVO queryVO) {
        SysUser login = SecurityUtils.getUser();
        // 1.判断登录用户是否为教师
        if (login.getIdentity() != LoginIdentity.KG_ADMIN) {
            ClassroomPartBO partBO = classroomService.getPartByUser(login.getAssociationId(), login.getKgId());
            if (partBO != null) {
                queryVO.setClassroomId(partBO.getId());
            }
        }
        // 2.分页查询
        IPage<StudentVacateApplyBO> page = queryVO.page();
        List<StudentVacateApplyBO> list = baseMapper.listHistoryPage(page, queryVO);
        page.setRecords(list);
        // 3.查询申请人名称
        if (!CollectionUtils.isEmpty(list)) {
            List<Guardian> guardians = remoteGuardianService.getByStudentIdsAndPhones(
                    list.stream().map(StudentVacateApplyBO::getStudentId).collect(Collectors.toList()),
                    list.stream().map(StudentVacateApplyBO::getPhone).collect(Collectors.toList())
            );
            Map<String, String> map = guardians.stream().collect(Collectors.toMap(
                    Guardian::getPhone,
                    Guardian::getName
            ));
            for (StudentVacateApplyBO bo : list) {
                bo.setApplyName(map.get(bo.getPhone()));
            }
        }
        return page;
    }

    /**
     * 获取待处理的儿童请假审批数量
     *
     * @param kgId 校区id
     * @return 结果
     */
    @Override
    public int getPendingCountByKgId(Long kgId) {
        return super.count(
                new QueryWrapper<StudentVacateApply>().lambda()
                        .eq(StudentVacateApply::getKgId, kgId)
                        .eq(StudentVacateApply::getStatus, ApplyStatus.PENDING)
        );
    }

    /**
     * 获取指定数量的前N条待审批的复课申请, 首页显示
     *
     * @param kgId   校区id
     * @param number 条数
     * @return 结果
     */
    @Override
    public List<KgHomePendingItemBO> getHomePendingTopByKgId(Long kgId, int number) {
        IPage<KgHomePendingItemBO> page = new Page<>(Constants.ONE, number);
        return baseMapper.getHomePendingTopByKgId(page, kgId);
    }

    /**
     * 获取学生请假记录
     *
     * @param queryVO 入参
     * @return 结果
     */
    @Override
    public List<StudentVacateApplyReportBO> getStudentVacateApplyByIds(StudentVacateApplyReportQueryVO queryVO) {
        return baseMapper.getStudentVacateApplyByIds(queryVO);
    }

    /**
     * 统一请假的记录
     *
     * @param studentIds 学生id结合
     * @param beginDate  开始日期
     * @param endDate    结束日期
     * @return 结果
     */
    @Override
    public List<StudentVacateApply> getConsentByStudentIdAndDateRange(List<Long> studentIds, LocalDate beginDate, LocalDate endDate) {
        return baseMapper.getConsentByStudentIdAndDateRange(studentIds, beginDate, endDate);
    }

    /**
     * 获取校区下日期内同意的请假记录
     *
     * @param kgId 校区id
     * @param date 日期
     */
    @Override
    public List<StudentVacateApply> getConsentByKgIdAndDate(Long kgId, LocalDate date) {
        return baseMapper.getConsentByKgIdAndDate(kgId, date);
    }
}
