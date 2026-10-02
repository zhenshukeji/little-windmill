package com.zhenshu.parent.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.parent.app.domain.bo.my.MyClassroomBO;
import com.zhenshu.parent.app.domain.po.Classroom;
import com.zhenshu.parent.app.domain.po.Student;
import com.zhenshu.parent.app.mapper.ClassroomMapper;
import com.zhenshu.parent.app.service.ClassroomService;
import com.zhenshu.parent.app.service.KindergartenStaffService;
import com.zhenshu.parent.app.service.StudentService;
import com.zhenshu.parent.common.constant.enums.TeacherPost;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/27 17:05
 * @desc
 */
@Service
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper, Classroom> implements ClassroomService {
    @Resource
    private StudentService studentService;
    @Resource
    private KindergartenStaffService kindergartenStaffService;

    /**
     * 获取班级
     *
     * @param classId 班级id
     * @return 结果
     */
    @Override
    public MyClassroomBO myClass(Long classId) {
        return baseMapper.myClass(classId);
    }

    }
