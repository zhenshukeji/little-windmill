package com.zhenshu.parent.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhenshu.parent.app.domain.bo.circle.ClassCircleBO;
import com.zhenshu.parent.app.domain.po.ClassCircle;

import java.util.List;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-29
 * @desc 班级圈 mapper
 */
public interface ClassCircleMapper extends BaseMapper<ClassCircle> {
    /**
     * 获取班级圈列表
     *
     * @param kgId 学生信息
     * @return 结果
     */
    List<ClassCircleBO> listQuery(Long kgId);
}
