package com.zhenshu.system.business.kg.home.facade;

import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.core.domain.model.LoginUser;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomPartBO;
import com.zhenshu.system.business.kg.home.domain.bo.KgHomeBO;
import com.zhenshu.system.business.kg.home.domain.bo.KgHomePendingBO;
import com.zhenshu.system.business.kg.home.domain.bo.KgHomePendingItemBO;
import com.zhenshu.system.remote.kg.base.advanced.RemoteClassroomService;
import com.zhenshu.system.remote.kg.work.backlog.RemoteStudentVacateApplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/9 19:02
 * @desc 首页聚合。2026-10-01 社区版收窄（ce-04）：仅保留学生请假待办与班级列表，
 *       财务/考勤/喂药/复课/缴费聚合与 classAttendanceDate 随商业模块移除。
 */
@Service
public class KgHomeFacade {

    @Resource
    private RemoteStudentVacateApplyService remoteStudentVacateApplyService;
    @Resource
    private RemoteClassroomService remoteClassroomService;

    /**
     * 获取首页数据（社区版：仅学生请假待办）
     *
     * @return 结果
     */
    public KgHomeBO home() {
        KgHomeBO bo = new KgHomeBO();
        Long kgId = SecurityUtils.getUserKgId();
        if (SecurityUtils.getLoginUser().getPermissions().contains("kg:work:backlog:studentVacate:all")) {
            Integer count = remoteStudentVacateApplyService.getPendingCountByKgId(kgId);
            List<KgHomePendingItemBO> list = Collections.emptyList();
            if (count > Constants.ZERO) {
                list = remoteStudentVacateApplyService.getHomePendingTopByKgId(kgId, 3);
            }
            KgHomePendingBO studentVacate = new KgHomePendingBO();
            studentVacate.setPendingCount(count);
            studentVacate.setPendingList(list);
            bo.setStudentVacate(studentVacate);
        }
        return bo;
    }

    /**
     * 获取班级
     *
     * @param kgId 校区id
     * @return 结果
     */
    public List<ClassroomPartBO> classList(Long kgId) {
        return remoteClassroomService.getPartByKgId(kgId);
    }
}
