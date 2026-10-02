package com.zhenshu.system.business.kg.work.backlog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.system.business.kg.home.domain.bo.KgHomePendingItemBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyCheckingBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyDetailsBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyReportBO;
import com.zhenshu.system.business.kg.work.backlog.domain.po.StudentVacateApply;
import com.zhenshu.system.business.kg.work.backlog.domain.vo.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-05-12
 * @desc service
 */
public interface IStudentVacateApplyService extends IService<StudentVacateApply> {
    /**
     * 列表查询
     *
     * @param queryVO 列表查询入参
     * @return 结果
     */
    IPage<StudentVacateApplyBO> listPendingPage(StudentVacateApplyQueryVO queryVO);

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    void updateById(StudentVacateApplyEditVO editVO);

    /**
     * 根据Id查询
     *
     * @param id id
     * @return 结果
     */
    StudentVacateApplyDetailsBO getDetailsById(Long id);


    /**
     * 获取并校验当前用户是否能查看和操作id对应的数据
     *
     * @param id id
     * @return 结果
     */
    StudentVacateApply getAndVerifyStudentVacateApply(Long id);

    /**
     * 获取指定日期中指定的學生已同意的请假记录
     *
     * @param queryVO 入参
     * @return 结果
     */
    List<StudentVacateApplyCheckingBO> getConsentByStudentIdAndDate(StudentVacateApplyDateQueryVO queryVO);

    /**
     * 根据时间范围查询请假申请
     *
     * @param queryVO 入参
     * @return 结果
     */
    List<StudentVacateApplyCheckingBO> getConsentByStudentIdAndDateRange(StudentVacateApplyDateRangeQueryVO queryVO);

    /**
     * 列表查询申请历史
     *
     * @param queryVO 入参
     * @return 结果
     */
    IPage<StudentVacateApplyBO> listHistoryPage(StudentVacateApplyQueryVO queryVO);

    /**
     * 获取待处理的儿童请假审批数量
     *
     * @param kgId 校区id
     * @return 结果
     */
    int getPendingCountByKgId(Long kgId);

    /**
     * 获取指定数量的前N条待审批的复课申请, 首页显示
     *
     * @param kgId   校区id
     * @param number 条数
     * @return 结果
     */
    List<KgHomePendingItemBO> getHomePendingTopByKgId(Long kgId, int number);

    /**
     * 获取学生请假记录
     *
     * @param queryVO 入参
     * @return 结果
     */
    List<StudentVacateApplyReportBO> getStudentVacateApplyByIds(StudentVacateApplyReportQueryVO queryVO);

    /**
     * 统一请假的记录
     *
     * @param studentIds 学生id结合
     * @param beginDate  开始日期
     * @param endDate    结束日期
     * @return 结果
     */
    List<StudentVacateApply> getConsentByStudentIdAndDateRange(List<Long> studentIds, LocalDate beginDate, LocalDate endDate);

    /**
     * 获取校区下日期内同意的请假记录
     *
     * @param kgId 校区id
     * @param date 日期
     */
    List<StudentVacateApply> getConsentByKgIdAndDate(Long kgId, LocalDate date);
}
