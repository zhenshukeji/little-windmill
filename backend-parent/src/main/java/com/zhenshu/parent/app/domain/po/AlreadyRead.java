package com.zhenshu.parent.app.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.parent.common.constant.enums.AlreadyReadType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/29 16:12
 * @desc 已读表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mini_already_read")
public class AlreadyRead extends BasePO {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联id
     */
    private Long associationId;

    /**
     * 关联类型
     */
    private AlreadyReadType type;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 最后阅读时间
     */
    private LocalDateTime lastReadTime;
}
