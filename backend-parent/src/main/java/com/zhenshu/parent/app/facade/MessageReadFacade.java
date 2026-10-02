package com.zhenshu.parent.app.facade;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.parent.app.domain.bo.message.MessageReadIdBO;
import com.zhenshu.parent.app.domain.bo.message.MessageReadTimeBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.domain.po.AlreadyRead;
import com.zhenshu.parent.app.domain.po.LastReadTime;
import com.zhenshu.parent.app.service.AlreadyReadService;
import com.zhenshu.parent.app.service.LastReadTimeService;
import com.zhenshu.parent.common.constant.Constants;
import com.zhenshu.parent.common.constant.enums.AlreadyReadType;
import com.zhenshu.parent.common.constant.enums.LastReadTimeType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/29 19:32
 * @desc 消息已读设置
 */
@Service
public class MessageReadFacade {
    @Resource
    private AlreadyReadService alreadyReadService;
    @Resource
    private LastReadTimeService lastReadTimeService;

    /**
     * 设置是否已读; 根据ID的方式
     *
     * @param type 类型
     * @param list 数据集合
     */
    public <M extends MessageReadIdBO> void setReadById(AlreadyReadType type, List<M> list) {
        List<AlreadyRead> reads = Collections.emptyList();
        if (!CollectionUtils.isEmpty(list)) {
            Set<Long> ids = list.stream().filter(M::isNeedRead).map(MessageReadIdBO::getId).collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(ids)) {
                reads = alreadyReadService.getByAssociationIdsAndType(ids, type);
            }
        }
        Map<Long, AlreadyRead> map = reads.stream().collect(Collectors.toMap(
                AlreadyRead::getAssociationId,
                item -> item
        ));
        for (M m : list) {
            if (!m.isNeedRead()) {
                m.setIsRead(Constants.TRUE);
                continue;
            }
            AlreadyRead read = map.get(m.getId());
            boolean result;
            if (read == null) {
                // 没有阅读记录, 未读
                result = false;
            } else if (m.getUpdateTime() == null) {
                // 有阅读记录 && 最后一次修改时间为null, 已读
                result = true;
            } else if (m.getUpdateTime().compareTo(read.getLastReadTime()) > Constants.ZERO) {
                // 有阅读记录 && 有修改时间 && 修改时间 > 最后一次阅读时间, 未读
                result = false;
            } else {
                result = true;
            }
            m.setIsRead(result);
        }
    }

    /**
     * 设置是否已读; 根据ID的方式
     *
     * @param type 类型
     * @param page 数据集合
     */
    public <M extends MessageReadIdBO> void setReadById(AlreadyReadType type, IPage<M> page) {
        this.setReadById(type, page.getRecords());
    }

    /**
     * 设置消息已读
     *
     * @param bo        数据
     * @param type      类型
     * @param loginUser 登录用户
     */
    public void alreadyReadById(MessageReadIdBO bo, AlreadyReadType type, LoginUser loginUser) {
        if (!bo.isNeedRead()) {
            return;
        }
        // 1.获取已读记录
        AlreadyRead alreadyRead = alreadyReadService.getReadRecord(bo.getId(), type, loginUser.getStudentId());
        // 2.添加
        AlreadyRead data = new AlreadyRead();
        if (alreadyRead == null) {
            data.initCreateProp();
            data.setStudentId(loginUser.getStudentId());
            data.setAssociationId(bo.getId());
            data.setType(type);
            data.setCreateBy(loginUser.getUserId());
        } else {
            data.initUpdateProp();
            data.setId(alreadyRead.getId());
            data.setUpdateBy(loginUser.getUserId());
        }
        data.setLastReadTime(LocalDateTime.now());
        alreadyReadService.saveOrUpdate(data);
    }

    /**
     * 设置是否已读; 根据最后一次阅读时间
     *
     * @param type 类型
     * @param page 数据集合
     */
    public <M extends MessageReadTimeBO> void setReadByTime(LastReadTimeType type, IPage<M> page, LoginUser loginUser) {
        if (page.getCurrent() == Constants.ONE) {
            // 只有第一页需要判断是否已读
            this.setReadByTime(type, page.getRecords(), loginUser);
        } else {
            // 其他页都必定是已读的
            for (M m : page.getRecords()) {
                m.setIsRead(Constants.FALSE);
            }
        }
    }

    /**
     * 设置是否已读; 根据最后一次阅读时间
     *
     * @param type 类型
     * @param list 数据集合
     */
    public <M extends MessageReadTimeBO> void setReadByTime(LastReadTimeType type, List<M> list, LoginUser loginUser) {
        LastReadTime lastReadTime = lastReadTimeService.getByTypeAndStudentId(type, loginUser.getStudentId());
        LocalDateTime time;
        if (Objects.isNull(lastReadTime)) {
            time = LocalDateTime.MIN;
        } else {
            time = lastReadTime.getTime();
        }
        for (M m : list) {
            m.setIsRead(time.compareTo(m.getTime()) >= Constants.ZERO);
        }
        LastReadTime data = new LastReadTime();
        if (Objects.isNull(lastReadTime)) {
            data.initCreateProp();
            data.setStudentId(loginUser.getStudentId());
            data.setType(type);
            data.setCreateBy(loginUser.getUserId());
        } else {
            data.initUpdateProp();
            data.setId(lastReadTime.getId());
            data.setUpdateBy(loginUser.getUserId());
        }
        data.setTime(LocalDateTime.now());
        lastReadTimeService.saveOrUpdate(data);
    }
}
