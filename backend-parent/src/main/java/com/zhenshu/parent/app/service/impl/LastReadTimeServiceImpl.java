package com.zhenshu.parent.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.parent.app.domain.po.LastReadTime;
import com.zhenshu.parent.app.mapper.LastReadTimeMapper;
import com.zhenshu.parent.app.service.LastReadTimeService;
import com.zhenshu.parent.common.constant.Constants;
import com.zhenshu.parent.common.constant.enums.LastReadTimeType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/7/1 11:55
 * @desc
 */
@Service
public class LastReadTimeServiceImpl extends ServiceImpl<LastReadTimeMapper, LastReadTime> implements LastReadTimeService {
    /**
     * 获取学生在对应消息的最后阅读时间数据
     *
     * @param type      类型
     * @param studentId 学生id
     * @return 结果
     */
    @Override
    public LastReadTime getByTypeAndStudentId(LastReadTimeType type, Long studentId) {
        List<LastReadTime> list = super.list(
                new QueryWrapper<LastReadTime>().lambda()
                        .eq(LastReadTime::getStudentId, studentId)
                        .eq(LastReadTime::getType, type)
                        .orderByDesc(LastReadTime::getId)
        );
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(Constants.ZERO);
    }

    /**
     * 获取指定学生的所有记录
     *
     * @param studentId 学生id
     * @return 结果
     */
    @Override
    public List<LastReadTime> getListByStudentId(Long studentId) {
        return super.list(
                new QueryWrapper<LastReadTime>().lambda()
                        .eq(LastReadTime::getStudentId, studentId)
        );
    }
}
