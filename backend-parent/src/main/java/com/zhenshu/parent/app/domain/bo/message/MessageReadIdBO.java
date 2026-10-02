package com.zhenshu.parent.app.domain.bo.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/29 19:31
 * @desc 消息阅读-ID
 */
@Data
public abstract class MessageReadIdBO {
    /**
     * 是否已读
     */
    @ApiModelProperty(value = "是否已读")
    private Boolean isRead;

    /**
     * 获取唯一标识
     *
     * @return 结果
     */
    public abstract Long getId();

    /**
     * 判断是否需要设置isRead属性, false将导致isRead总是为true
     *
     * @return 结果
     */
    @JsonIgnore
    public abstract Boolean isNeedRead();

    /**
     * 获取数据的最后一次修改时间
     * 修改时间 > 最后一次阅读时间会被认为是未读的消息,
     * 修改时间 == null, 将不会比较时间, 只要存在阅读记录就认为是已读的消息
     *
     * @return 结果
     */
    public LocalDateTime getUpdateTime(){
        return null;
    }
}
