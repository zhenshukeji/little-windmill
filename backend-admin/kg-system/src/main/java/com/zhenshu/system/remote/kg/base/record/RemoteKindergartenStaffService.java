package com.zhenshu.system.remote.kg.base.record;

import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenTeacherBO;
import com.zhenshu.system.business.kg.base.record.domain.po.KindergartenStaff;
import com.zhenshu.system.business.kg.base.record.service.IKindergartenStaffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jing
 * @version 1.0
 * @desc 远程班级调用
 * @date 2022/2/16 0016 19:17
 **/
@Service
public class RemoteKindergartenStaffService {

    @Resource
    private IKindergartenStaffService kindergartenStaffService;

    public KindergartenStaff getAndVerifyKgStaffByUid(Long uid) {
        return kindergartenStaffService.getAndVerifyKgStaffByUid(uid);
    }

    public boolean isTeacher(SysUser sysUser) {
        return kindergartenStaffService.isTeacher(sysUser);
    }

    public List<KindergartenTeacherBO> getTeacherIds(Long kgId){
        return kindergartenStaffService.getTeacherIds(kgId);
    }

}
