package com.zhenshu.parent.app.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.parent.common.constant.enums.LastReadTimeType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/7/1 11:51
 * @desc 最后一次阅读时间
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mini_last_read_time")
public class LastReadTime extends BasePO {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 最后阅读时间
     */
    private LocalDateTime time;

    /**
     * 类型
     */
    private LastReadTimeType type;
}
