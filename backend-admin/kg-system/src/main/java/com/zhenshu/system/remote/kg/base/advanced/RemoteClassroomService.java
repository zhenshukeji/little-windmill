package com.zhenshu.system.remote.kg.base.advanced;

import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomBO;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomPartBO;
import com.zhenshu.system.business.kg.base.advanced.domain.po.Classroom;
import com.zhenshu.system.business.kg.base.advanced.service.IClassroomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author jing
 * @version 1.0
 * @desc 远程班级调用
 * @date 2022/2/16 0016 19:17
 **/
@Service
public class RemoteClassroomService {

    @Resource
    private IClassroomService classroomService;

    public List<ClassroomBO> getByKgId(Long kgId) {
        return classroomService.getByKgId(kgId);
    }

    public List<ClassroomPartBO> getPartByKgId(Long kgId) {
        return classroomService.getPartByKgId(kgId);
    }

    public ClassroomPartBO getPartByUser(Long kgStaffId, Long kgId) {
        return classroomService.getPartByUser(kgStaffId, kgId);
    }

    public Classroom getStaffBindClassroom(Long staffId) {
        return classroomService.getStaffBindClassroom(staffId);
    }

        public Classroom getAndVerifyClassroom(Long classId) {
        return classroomService.getAndVerifyClassroom(classId);
    }

    /**
     * 根据班级名称获取班级
     *
     * @param classNames 班级名称集合
     * @param kgId       校区id
     * @return 结果
     */
    public List<Classroom> getByClassNameAndKgId(Collection<String> classNames, Long kgId) {
        return classroomService.getByClassNameAndKgId(classNames, kgId);
    }

    /**
     * 获取指定班级
     *
     * @param classId 班级id
     * @return 结果
     */
    public Classroom getById(Long classId) {
        return classroomService.getById(classId);
    }
}
