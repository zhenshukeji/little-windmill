package com.zhenshu.system.remote.kg.base.record;

import com.zhenshu.system.business.kg.base.record.domain.po.Guardian;
import com.zhenshu.system.business.kg.base.record.service.IGuardianService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/7 16:03
 * @desc
 */
@Service
public class RemoteGuardianService {
    @Resource
    private IGuardianService guardianService;

    public Guardian getByStudentIdAndPhone(Long studentId, String phone){
        return guardianService.getByStudentIdAndPhone(studentId, phone);
    }

    /**
     * 获取监护人
     *
     * @param students 学生id集合
     * @param phones 手机号集合
     * @return 结果
     */
    public List<Guardian> getByStudentIdsAndPhones(List<Long> students, List<String> phones) {
        return guardianService.getByStudentIdsAndPhones(students, phones);
    }
}
