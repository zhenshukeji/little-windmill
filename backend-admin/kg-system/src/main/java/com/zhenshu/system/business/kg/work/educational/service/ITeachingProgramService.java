package com.zhenshu.system.business.kg.work.educational.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomPartBO;
import com.zhenshu.system.business.kg.work.educational.domain.bo.TeachingProgramBO;
import com.zhenshu.system.business.kg.work.educational.domain.po.TeachingProgram;
import com.zhenshu.system.business.kg.work.educational.domain.vo.TeachingProgramAddVO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.TeachingProgramDeleteVO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.TeachingProgramQueryVO;

import java.util.List;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-09
 * @desc 教学计划 Service
 */
public interface ITeachingProgramService extends IService<TeachingProgram> {
    /**
     * 列表查询
     *
     * @param queryVo 列表查询入参
     * @return 结果
     */
    IPage<TeachingProgramBO> listPage(TeachingProgramQueryVO queryVo);

    /**
     * 添加教学计划
     *
     * @param addVo 添加入参
     */
    void insert(TeachingProgramAddVO addVo);

    /**
     * 删除教学计划
     *
     * @param deleteVo 删除入参
     */
    void deleteById(TeachingProgramDeleteVO deleteVo);

    /**
     * 查询和校验当前用户是否能查看和操作id对应数据
     *
     * @param id 教学计划id
     * @return 结果
     */
    TeachingProgram getAndVerifyTeachingProgram(Long id);

    /**
     * 获取班级所有信息
     *
     * @return 结果
     */
    List<ClassroomPartBO> getClassroomByKgId();
}
