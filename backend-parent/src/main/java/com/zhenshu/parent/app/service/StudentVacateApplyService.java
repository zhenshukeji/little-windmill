package com.zhenshu.parent.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.parent.app.domain.bo.apply.VacateApplyIdBO;
import com.zhenshu.parent.app.domain.bo.apply.VacateApplyDetailsBO;
import com.zhenshu.parent.app.domain.po.StudentVacateApply;
import com.zhenshu.parent.common.utils.page.PageEntity;

import java.util.Date;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/24 15:48
 * @desc
 */
public interface StudentVacateApplyService extends IService<StudentVacateApply> {

            /**
     * 分页查询
     *
     * @param studentId 学生id
     * @param queryVO   入参
     * @return 结果
     */
    IPage<VacateApplyIdBO> listPage(Long studentId, PageEntity queryVO);

    /**
     * 获取详情
     *
     * @param vacateApplyId 请假申请id
     * @return 结果
     */
    VacateApplyDetailsBO getDetailsById(Long vacateApplyId);
}
