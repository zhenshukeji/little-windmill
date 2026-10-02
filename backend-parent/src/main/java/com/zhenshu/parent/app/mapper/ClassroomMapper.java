package com.zhenshu.parent.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhenshu.parent.app.domain.bo.my.MyClassroomBO;
import com.zhenshu.parent.app.domain.po.Classroom;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/27 17:03
 * @desc
 */
public interface ClassroomMapper extends BaseMapper<Classroom> {
    /**
     * 获取班级
     *
     * @param classId 班级id
     * @return 结果
     */
    MyClassroomBO myClass(Long classId);
}
