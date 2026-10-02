package com.zhenshu.system.remote.kg.work.backlog;

import com.zhenshu.system.business.kg.home.domain.bo.KgHomePendingItemBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyCheckingBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyReportBO;
import com.zhenshu.system.business.kg.work.backlog.domain.po.StudentVacateApply;
import com.zhenshu.system.business.kg.work.backlog.domain.vo.StudentVacateApplyDateQueryVO;
import com.zhenshu.system.business.kg.work.backlog.domain.vo.StudentVacateApplyDateRangeQueryVO;
import com.zhenshu.system.business.kg.work.backlog.domain.vo.StudentVacateApplyReportQueryVO;
import com.zhenshu.system.business.kg.work.backlog.service.IStudentVacateApplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * @author zch
 * @version 1.0
 * @desc 远程学生请假申请调用
 * @date 2022/2/16 0016 19:17
 **/
@Service
public class RemoteStudentVacateApplyService {

    @Resource
    private IStudentVacateApplyService studentVacateApplyService;

    public List<StudentVacateApplyCheckingBO> getConsentByStudentIdAndDate(StudentVacateApplyDateQueryVO queryVO) {
        return studentVacateApplyService.getConsentByStudentIdAndDate(queryVO);
    }

    public List<StudentVacateApplyCheckingBO> getByStudentIdAndDate(StudentVacateApplyDateRangeQueryVO queryVO) {
        return studentVacateApplyService.getConsentByStudentIdAndDateRange(queryVO);
    }

    /**
     * 获取待审批的请假申请总数
     *
     * @param kgId 校区id
     * @return 结果
     */
    public Integer getPendingCountByKgId(Long kgId) {
        return studentVacateApplyService.getPendingCountByKgId(kgId);
    }

    /**
     * 获取指定数量的前N条待审批的复课申请, 首页显示
     *
     * @param kgId   校区id
     * @param number 条数
     * @return 结果
     */
    public List<KgHomePendingItemBO> getHomePendingTopByKgId(Long kgId, int number) {
        return studentVacateApplyService.getHomePendingTopByKgId(kgId, number);
    }

    public List<StudentVacateApplyReportBO> getStudentVacateApplyByIds(StudentVacateApplyReportQueryVO queryVO) {
        return studentVacateApplyService.getStudentVacateApplyByIds(queryVO);
    }

    /**
     * 统一请假的记录
     *
     * @param studentIds 学生id结合
     * @param beginDate  开始日期
     * @param endDate    结束日期
     * @return 结果
     */
    public List<StudentVacateApply> getConsentByStudentIdAndDateRange(List<Long> studentIds, LocalDate beginDate, LocalDate endDate) {
        return studentVacateApplyService.getConsentByStudentIdAndDateRange(studentIds, beginDate, endDate);
    }

    /**
     * 获取校区下日期内同意的请假记录
     *
     * @param kgId 校区id
     * @param date 日期
     */
    public List<StudentVacateApply> getConsentByKgIdAndDate(Long kgId, LocalDate date) {
        return studentVacateApplyService.getConsentByKgIdAndDate(kgId, date);
    }
}
