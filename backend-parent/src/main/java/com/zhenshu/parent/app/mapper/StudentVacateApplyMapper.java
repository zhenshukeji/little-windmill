package com.zhenshu.parent.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhenshu.parent.app.domain.bo.apply.VacateApplyDetailsBO;
import com.zhenshu.parent.app.domain.po.StudentVacateApply;

import java.util.Date;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/24 15:47
 * @desc
 */
public interface StudentVacateApplyMapper extends BaseMapper<StudentVacateApply> {
           /**
     * 获取详情
     *
     * @param vacateApplyId 请假申请id
     * @return 结果
     */
    VacateApplyDetailsBO getDetailsById(Long vacateApplyId);

}
