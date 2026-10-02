package com.zhenshu.system.business.kg.work.backlog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.system.business.kg.home.domain.bo.KgHomePendingItemBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyCheckingBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyDetailsBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyReportBO;
import com.zhenshu.system.business.kg.work.backlog.domain.po.StudentVacateApply;
import com.zhenshu.system.business.kg.work.backlog.domain.vo.StudentVacateApplyDateQueryVO;
import com.zhenshu.system.business.kg.work.backlog.domain.vo.StudentVacateApplyDateRangeQueryVO;
import com.zhenshu.system.business.kg.work.backlog.domain.vo.StudentVacateApplyQueryVO;
import com.zhenshu.system.business.kg.work.backlog.domain.vo.StudentVacateApplyReportQueryVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-03-07
 * @desc 学生请假申请 Mapper接口
 */
public interface StudentVacateApplyMapper extends BaseMapper<StudentVacateApply> {
    /**
     * 列表查询
     *
     * @param page    分页对象
     * @param queryVO 入参VO
     * @return 结果
     */
    List<StudentVacateApplyBO> listPendingPage(@Param("page") IPage<StudentVacateApplyBO> page, @Param("queryVO") StudentVacateApplyQueryVO queryVO);

    /**
     * 根据Id查询
     *
     * @param id id
     * @return 结果
     */
    StudentVacateApplyDetailsBO getDetailsById(Long id);

    /**
     * 获取指定日期中指定学生的请假记录
     *
     * @param queryVO 入参
     * @return 结果
     */
    List<StudentVacateApplyCheckingBO> getByStudentIdAndDate(@Param("queryVO") StudentVacateApplyDateQueryVO queryVO);

    /**
     * 获取指定日期中指定学生的请假记录
     *
     * @param queryVO 入参
     * @return 结果
     */
    List<StudentVacateApplyCheckingBO> getByStudentIdAndDateRange(@Param("queryVO") StudentVacateApplyDateRangeQueryVO queryVO);

    /**
     * 列表查询申请历史
     *
     * @param page    分页参数
     * @param queryVO 入参
     * @return 结果
     */
    List<StudentVacateApplyBO> listHistoryPage(@Param("page") IPage<StudentVacateApplyBO> page, @Param("queryVO") StudentVacateApplyQueryVO queryVO);

    /**
     * 获取指定数量的前N条待审批的请假申请, 首页显示
     *
     * @param page 分页对象
     * @param kgId 校区id
     * @return 结果
     */
    List<KgHomePendingItemBO> getHomePendingTopByKgId(@Param("page") IPage<KgHomePendingItemBO> page, @Param("kgId") Long kgId);

    /**
     * 获取指定学生请假记录
     *
     * @param queryVO 入参
     * @return 结果
     */
    List<StudentVacateApplyReportBO> getStudentVacateApplyByIds(@Param("queryVO") StudentVacateApplyReportQueryVO queryVO);

    /**
     * 统一请假的记录
     *
     * @param studentIds 学生id结合
     * @param beginDate  开始日期
     * @param endDate    结束日期
     * @return 结果
     */
    List<StudentVacateApply> getConsentByStudentIdAndDateRange(@Param("studentIds") List<Long> studentIds, @Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);

    /**
     * 获取校区下日期内同意的请假记录
     *
     * @param kgId 校区id
     * @param date 日期
     */
    List<StudentVacateApply> getConsentByKgIdAndDate(@Param("kgId") Long kgId, @Param("date") LocalDate date);
}
