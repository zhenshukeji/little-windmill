package com.zhenshu.parent.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhenshu.parent.app.domain.bo.StudentBO;
import com.zhenshu.parent.app.domain.bo.program.TeachingProgramDetailBO;
import com.zhenshu.parent.app.domain.bo.program.TeachingProgramPartBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.domain.po.Student;
import com.zhenshu.parent.app.domain.po.TeachingProgram;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 教学计划表 Mapper 接口
 * </p>
 *
 * @author zch
 * @since 2022-06-21
 */
public interface TeachingProgramMapper extends BaseMapper<TeachingProgram> {
    /**
     * 获取教学计划列表
     *
     * @param studentId 学生id
     * @return 结果
     */
    List<TeachingProgramPartBO> getList(Long studentId);

    /**
     * 获取教学计划详情
     *
     * @param student 登录用户信息
     * @param id 教学计划id
     * @return 结果
     */
    TeachingProgramDetailBO getListDetail(@Param("student") StudentBO student, @Param("id") Long id);
}
