package com.zhenshu.parent.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.parent.app.domain.bo.program.TeachingProgramBO;
import com.zhenshu.parent.app.domain.bo.program.TeachingProgramDetailBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.domain.po.TeachingProgram;

import java.util.List;

/**
 * <p>
 * 教学计划
 * </p>
 *
 * @author zch
 * @since 2022-06-21
 */
public interface TeachingProgramService extends IService<TeachingProgram> {

    /**
     * 获取学生班级教学计划
     *
     * @param studentId 学生id
     * @return 结果
     */
    List<TeachingProgramBO> getList(Long studentId);

    /**
     * 获取教学计划的详情
     *
     * @param login 登录用户信息
     * @param id 教学计划详情 id
     * @return 结果
     */
    TeachingProgramDetailBO getListDetail(LoginUser login,Long id);

}
