package com.zhenshu.parent.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.parent.app.domain.bo.StudentBO;
import com.zhenshu.parent.app.domain.bo.checking.CheckingStudentDataBO;
import com.zhenshu.parent.app.domain.po.Student;
import com.zhenshu.parent.app.mapper.StudentMapper;
import com.zhenshu.parent.app.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学生表  服务实现类
 * </p>
 *
 * @author jing
 * @since 2022-05-24
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public List<StudentBO> getByIdsAndNotLeave(List<Long> studentIds) {
        return this.baseMapper.getByIdsAndNotLeave(studentIds);
    }

    /**
     * 获取出勤学生信息
     *
     * @param studentBO 登录用户信息
     * @return 结果
     */
    @Override
    public CheckingStudentDataBO checkingStudentData(StudentBO studentBO) {
        return this.baseMapper.checkingStudentData(studentBO);
    }
}
