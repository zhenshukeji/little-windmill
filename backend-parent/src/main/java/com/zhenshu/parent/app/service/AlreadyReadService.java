package com.zhenshu.parent.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.parent.app.domain.po.AlreadyRead;
import com.zhenshu.parent.common.constant.enums.AlreadyReadType;

import java.util.Collection;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/29 16:15
 * @desc
 */
public interface AlreadyReadService extends IService<AlreadyRead> {
    /**
     * 获取关联id是否已读
     *
     * @param ids  id集合
     * @param type 类型
     * @return 结果
     */
    List<AlreadyRead> getByAssociationIdsAndType(Collection<Long> ids, AlreadyReadType type);

    /**
     * 检查是否已经有这条阅读记录了
     *
     * @param id        id
     * @param type      类型
     * @param studentId 学生id
     * @return 结果
     */
    boolean hasReadRecord(Long id, AlreadyReadType type, Long studentId);

    /**
     * 获取阅读记录
     *
     * @param id        id
     * @param type      类型
     * @param studentId 学生id
     * @return 结果
     */
    AlreadyRead getReadRecord(Long id, AlreadyReadType type, Long studentId);
}
