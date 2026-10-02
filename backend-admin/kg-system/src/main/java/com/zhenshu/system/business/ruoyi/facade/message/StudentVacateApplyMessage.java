package com.zhenshu.system.business.ruoyi.facade.message;

import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.system.business.kg.work.backlog.service.IStudentVacateApplyService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/9 10:28
 * @desc 获取儿童请假审批未读消息数量
 */
@Component
public class StudentVacateApplyMessage implements MessageInterface {
    @Resource
    private IStudentVacateApplyService studentVacateApplyService;

    @Override
    public int getNotReadMessageCount() {
        return studentVacateApplyService.getPendingCountByKgId(SecurityUtils.getUserKgId());
    }
}
