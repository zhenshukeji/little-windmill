package com.zhenshu.parent.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.parent.app.domain.bo.StudentBO;
import com.zhenshu.parent.app.domain.bo.checking.CheckingStudentDataBO;
import com.zhenshu.parent.app.domain.po.Student;

import java.util.List;

/**
 * <p>
 * 学生表  服务类
 * </p>
 *
 * @author jing
 * @since 2022-05-24
 */
public interface StudentService extends IService<Student> {

    /**
     * 根据学生id查询
     *
     * @param studentIds 学生id列表
     * @return 学生列表
     */
    List<StudentBO> getByIdsAndNotLeave(List<Long> studentIds);

    /**
     * 获取出勤学生信息
     *
     * @param studentBO 登录用户信息
     * @return 结果
     */
    CheckingStudentDataBO checkingStudentData(StudentBO studentBO);
}
