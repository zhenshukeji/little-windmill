package com.zhenshu.parent.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.parent.app.domain.po.LastReadTime;
import com.zhenshu.parent.common.constant.enums.LastReadTimeType;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/7/1 11:54
 * @desc
 */
public interface LastReadTimeService extends IService<LastReadTime> {
    /**
     * 获取学生在对应消息的最后阅读时间数据
     *
     * @param type      类型
     * @param studentId 学生id
     * @return 结果
     */
    LastReadTime getByTypeAndStudentId(LastReadTimeType type, Long studentId);

    /**
     * 获取指定学生的所有记录
     *
     * @param studentId 学生id
     * @return 结果
     */
    List<LastReadTime> getListByStudentId(Long studentId);
}
