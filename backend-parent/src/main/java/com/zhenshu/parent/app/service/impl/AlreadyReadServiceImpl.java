package com.zhenshu.parent.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.parent.app.domain.po.AlreadyRead;
import com.zhenshu.parent.app.mapper.AlreadyReadMapper;
import com.zhenshu.parent.app.service.AlreadyReadService;
import com.zhenshu.parent.common.constant.Constants;
import com.zhenshu.parent.common.constant.enums.AlreadyReadType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/29 16:16
 * @desc
 */
@Service
public class AlreadyReadServiceImpl extends ServiceImpl<AlreadyReadMapper, AlreadyRead> implements AlreadyReadService {
    /**
     * 获取关联id是否已读
     *
     * @param ids  id集合
     * @param type 类型
     * @return 结果
     */
    @Override
    public List<AlreadyRead> getByAssociationIdsAndType(Collection<Long> ids, AlreadyReadType type) {
        return super.list(
                new QueryWrapper<AlreadyRead>().lambda()
                        .in(AlreadyRead::getAssociationId, ids)
                        .eq(AlreadyRead::getType, type)
        );
    }

    /**
     * 检查是否已经有这条阅读记录了
     *
     * @param id        id
     * @param type      类型
     * @param studentId 学生id
     * @return 结果
     */
    @Override
    public boolean hasReadRecord(Long id, AlreadyReadType type, Long studentId) {
        return super.count(
                new QueryWrapper<AlreadyRead>().lambda()
                        .eq(AlreadyRead::getStudentId, studentId)
                        .eq(AlreadyRead::getAssociationId, id)
                        .eq(AlreadyRead::getType, type)
        ) > Constants.ZERO;
    }

    /**
     * 获取阅读记录
     *
     * @param id        id
     * @param type      类型
     * @param studentId 学生id
     * @return 结果
     */
    @Override
    public AlreadyRead getReadRecord(Long id, AlreadyReadType type, Long studentId) {
        List<AlreadyRead> list = super.list(
                new QueryWrapper<AlreadyRead>().lambda()
                        .eq(AlreadyRead::getStudentId, studentId)
                        .eq(AlreadyRead::getAssociationId, id)
                        .eq(AlreadyRead::getType, type)
                        .orderByDesc(AlreadyRead::getId)
        );
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(Constants.ZERO);
        }
    }
}
