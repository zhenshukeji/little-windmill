package com.zhenshu.parent.app.domain.bo.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/7/1 12:00
 * @desc 消息阅读-时间
 */
@Data
public class MessageReadTimeBO {
    /**
     * 是否已读
     */
    @ApiModelProperty(value = "是否已读")
    private Boolean isRead;

    /**
     * 时间
     */
    @JsonIgnore
    @ApiModelProperty(value = "时间")
    private LocalDateTime time;
}
