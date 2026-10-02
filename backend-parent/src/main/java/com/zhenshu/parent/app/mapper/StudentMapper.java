package com.zhenshu.parent.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhenshu.parent.app.domain.bo.StudentBO;
import com.zhenshu.parent.app.domain.bo.checking.CheckingStudentDataBO;
import com.zhenshu.parent.app.domain.po.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学生表  Mapper 接口
 * </p>
 *
 * @author jing
 * @since 2022-05-24
 */
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 根据学生id查询
     *
     * @param studentIds 学生id列表
     * @return 学生列表
     */
    List<StudentBO> getByIdsAndNotLeave(@Param("studentIds") List<Long> studentIds);

    /**
     * 获取学生信息
     *
     * @param studentBO 登录用户信息
     * @return 结果
     */
    CheckingStudentDataBO checkingStudentData(@Param("studentBO") StudentBO studentBO);
}
