package com.zhenshu.framework.job;

import com.zhenshu.system.business.bloc.base.domain.po.Kindergarten;
import com.zhenshu.system.business.bloc.base.service.IKindergartenService;
import com.zhenshu.system.business.kg.work.checking.service.IStaffCheckingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/8/4 9:38
 * @desc 学生考勤初始化
 */
@Slf4j
@Component
public class StaffCheckInitSchedule {
    @Resource
    private IKindergartenService kindergartenService;
    @Resource
    private IStaffCheckingService staffCheckingService;

    /**
     * 员工考勤初始化
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void initStaffCheck() {
        log.info("员工考勤初始化");
        LocalDate now = LocalDate.now();
        // 1.获取所有校区
        List<Kindergarten> kindergartens = kindergartenService.list();
        for (Kindergarten kindergarten : kindergartens) {
            // 2.初始化
            staffCheckingService.initStaffCheck(now, kindergarten.getId(), kindergarten.getBlocId());
        }
    }
}
