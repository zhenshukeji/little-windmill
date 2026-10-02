package com.zhenshu.parent.app.facade.my;

import com.zhenshu.parent.app.domain.bo.my.GuardianBO;
import com.zhenshu.parent.app.domain.bo.my.MyBO;
import com.zhenshu.parent.app.domain.bo.my.MyClassroomBO;
import com.zhenshu.parent.app.domain.bo.my.MyInfoBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.domain.po.Classroom;
import com.zhenshu.parent.app.domain.po.Guardian;
import com.zhenshu.parent.app.domain.po.Kindergarten;
import com.zhenshu.parent.app.domain.po.Student;
import com.zhenshu.parent.app.domain.vo.my.GuardianEditVO;
import com.zhenshu.parent.app.domain.vo.my.MyInfoEditVO;
import com.zhenshu.parent.app.service.ClassroomService;
import com.zhenshu.parent.app.service.GuardianService;
import com.zhenshu.parent.app.service.KindergartenService;
import com.zhenshu.parent.app.service.StudentService;
import com.zhenshu.parent.common.constant.enums.ErrorEnums;
import com.zhenshu.parent.common.constant.exception.ServiceException;
import com.zhenshu.parent.common.utils.DateUtils;
import com.zhenshu.parent.common.utils.bean.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/27 17:08
 * @desc 我的
 */
@Service
public class MyFacade {
    @Resource
    private ClassroomService classroomService;
    @Resource
    private StudentService studentService;
    @Resource
    private GuardianService guardianService;
    @Resource
    private KindergartenService kindergartenService;

    /**
     * 我的班级
     *
     * @param loginUser 登录用户
     * @return 结果
     */
    public MyClassroomBO myClass(LoginUser loginUser) {
        return classroomService.myClass(loginUser.getClassId());
    }

    /**
     * 我的资料
     *
     * @param loginUser 登录用户
     * @return 结果
     */
    public MyInfoBO myInfo(LoginUser loginUser) {
        MyInfoBO bo = new MyInfoBO();
        // 1.获取学生
        Student student = studentService.getById(loginUser.getStudentId());
        if (Objects.isNull(student)) {
            throw new ServiceException(ErrorEnums.DATA_NOT_EXIST);
        }
        // 2.获取监护人信息
        List<Guardian> guardians = guardianService.getByStudentId(loginUser.getStudentId());
        // 3.组装对象
        bo.setStudentId(student.getId());
        bo.setStudentName(student.getName());
        bo.setAddress(student.getAddress());
        bo.setBirthdate(student.getBirthdate());
        bo.setGander(student.getGender());
        bo.setHeadImg(student.getImgUrl());
        bo.setAge(DateUtils.getAge(student.getBirthdate(), LocalDate.now()));
        bo.setGuardians(
                guardians.stream().map(item -> {
                    GuardianBO guardianBO = new GuardianBO();
                    BeanUtils.copyBeanProp(guardianBO, item);
                    return guardianBO;
                }).collect(Collectors.toList())
        );
        return bo;
    }

    /**
     * 修改我的资料
     *
     * @param loginUser 登录用户
     * @param editVO    修改入参
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(LoginUser loginUser, MyInfoEditVO editVO) {
        Set<Long> set = editVO.getGuardians().stream().map(GuardianEditVO::getId).collect(Collectors.toSet());
        if(set.size() != editVO.getGuardians().size()){
            throw new ServiceException(ErrorEnums.BAD_REQUEST);
        }
        // 1.修改学生
        Student updateStudent = new Student();
        updateStudent.setId(loginUser.getStudentId());
        updateStudent.setName(editVO.getStudentName());
        updateStudent.setGender(editVO.getGander());
        updateStudent.setImgUrl(editVO.getHeadImg());
        updateStudent.setBirthdate(editVO.getBirthdate());
        updateStudent.setAddress(editVO.getAddress());
        studentService.updateById(updateStudent);
        // 2.查询监护人
        List<Guardian> guardians = guardianService.listByIds(editVO.getGuardians().stream().map(GuardianEditVO::getId).collect(Collectors.toList()));
        // 3.判断登录用户是否能操作监护人; 判断监护人和学生是否是绑定关系
        for (Guardian guardian : guardians) {
            if (!Objects.equals(guardian.getKgId(), loginUser.getKgId())) {
                throw new ServiceException(ErrorEnums.DATA_NOT_EXIST);
            }
            if (!Objects.equals(guardian.getStudentId(), loginUser.getStudentId())) {
                throw new ServiceException(ErrorEnums.DATA_NOT_EXIST);
            }
        }
        // 4.修改监护人
        List<Guardian> guardianList = editVO.getGuardians().stream().map(item -> {
            Guardian guardian = new Guardian();
            BeanUtils.copyBeanProp(guardian, item);
            guardian.setUpdateBy(loginUser.getUserId());
            guardian.setUpdateTime(LocalDateTime.now());
            return guardian;
        }).collect(Collectors.toList());
        guardianService.updateBatchById(guardianList);
    }

    /**
     * 我的
     *
     * @param loginUser 登录用户
     * @return 结果
     */
    public MyBO my(LoginUser loginUser) {
        MyBO myBO = new MyBO();
        Student student = studentService.getById(loginUser.getStudentId());
        Classroom classroom = classroomService.getById(student.getClassId());
        Kindergarten kindergarten = kindergartenService.getById(student.getKgId());
        myBO.setKgName(kindergarten.getKindergartenName());
        myBO.setClassName(classroom.getClassName());
        myBO.setStudentName(student.getName());
        myBO.setImgUrl(student.getImgUrl());
        myBO.setAge(DateUtils.getAge(student.getBirthdate(), LocalDate.now()));
        return myBO;
    }
}
