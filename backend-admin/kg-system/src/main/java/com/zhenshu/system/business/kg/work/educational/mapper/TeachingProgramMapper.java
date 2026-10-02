package com.zhenshu.system.business.kg.work.educational.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.system.business.kg.work.educational.domain.bo.TeachingProgramBO;
import com.zhenshu.system.business.kg.work.educational.domain.po.TeachingProgram;
import com.zhenshu.system.business.kg.work.educational.domain.vo.TeachingProgramQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-09
 * @desc 教学计划 Mapper接口
 */
public interface TeachingProgramMapper extends BaseMapper<TeachingProgram> {
    /**
     * 列表查询
     *
     * @param page    分页
     * @param queryVO 查询入参
     * @return
     */
    List<TeachingProgramBO> listPage(@Param("page") IPage<TeachingProgramBO> page, @Param("queryVO") TeachingProgramQueryVO queryVO);
}
