package com.zhenshu.parent.app.domain.bo.health;

import com.zhenshu.parent.common.constant.enums.LastReadTimeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/7/1 15:37
 * @desc 消息阅读出参
 */
@Data
@ApiModel(description = "消息阅读出参")
public class MessageReadBO {
    /**
     * 是否有疾病消息
     */
    @ApiModelProperty(value = "是否有疾病消息")
    private Boolean hasIllnessMessage;

    /**
     * 是否有发病消息
     */
    @ApiModelProperty(value = "是否有发病消息")
    private Boolean hasInvasionMessage;

    /**
     * 是否有意外事故消息
     */
    @ApiModelProperty(value = "是否有意外事故消息")
    private Boolean hasAccidentMessage;

    /**
     * 是否有传染病消息
     */
    @ApiModelProperty(value = "是否有传染病消息")
    private Boolean hasContagionMessage;
}
