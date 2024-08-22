package com.zhenshu.system.business.ruoyi.facade.message;

import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.system.business.kg.health.health.service.IAccidentRecordService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/9 11:02
 * @desc 意外事故处理消息数量
 */
@Component
public class AccidentRecordMessage implements MessageInterface {
    @Resource
    private IAccidentRecordService accidentRecordService;

    @Override
    public int getNotReadMessageCount() {
        return accidentRecordService.getPendingCountByKgId(SecurityUtils.getUserKgId());
    }
}
