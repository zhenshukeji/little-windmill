package com.zhenshu.parent.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.parent.app.domain.bo.my.MyClassroomBO;
import com.zhenshu.parent.app.domain.po.Classroom;


/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/27 17:04
 * @desc
 */
public interface ClassroomService extends IService<Classroom> {
        /**
     * 获取班级
     *
     * @param classId 班级id
     * @return 结果
     */
    MyClassroomBO myClass(Long classId);
}
