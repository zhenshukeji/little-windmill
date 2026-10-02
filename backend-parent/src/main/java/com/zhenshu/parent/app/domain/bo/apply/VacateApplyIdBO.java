package com.zhenshu.parent.app.domain.bo.apply;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.parent.app.domain.bo.message.MessageReadIdBO;
import com.zhenshu.parent.common.constant.enums.ApplyStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/24 15:41
 * @desc 请假申请出参
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "请假申请出参")
public class VacateApplyIdBO extends MessageReadIdBO {
    /**
     * 请假id
     */
    @ApiModelProperty(value = "请假id")
    private Long id;

    /**
     * 请假类型 0病假 1事假
     */
    @ApiModelProperty(value = "请假类型 0病假 1事假")
    private Integer vacateType;

    /**
     * 请假开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "请假开始时间")
    private LocalDateTime beginTime;

    /**
     * 请假结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "请假结束时间")
    private LocalDateTime endTime;

    /**
     * 申请状态 0待处理 1已同意 2拒绝 3撤回
     */
    @ApiModelProperty(value = "申请状态 0待处理 1已同意 2拒绝 3撤回")
    private Integer status;

    /**
     * 请假原因
     */
    @ApiModelProperty(value = "请假原因")
    private String vacateReason;

    @Override
    public Boolean isNeedRead() {
        return !Objects.equals(this.status, ApplyStatus.PENDING.getCode());
    }
}
