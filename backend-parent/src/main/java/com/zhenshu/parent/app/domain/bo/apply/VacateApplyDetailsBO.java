package com.zhenshu.parent.app.domain.bo.apply;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.parent.common.constant.enums.ApplyStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/24 15:41
 * @desc 请假申请出参
 */
@Data
@ApiModel(description = "请假申请出参")
public class VacateApplyDetailsBO {
    /**
     * 请假id
     */
    @ApiModelProperty(value = "请假id")
    private Long id;

    /**
     * 请假原因
     */
    @ApiModelProperty(value = "请假原因")
    private String vacateReason;

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
    private ApplyStatus status;

    /**
     * 审批人
     */
    @ApiModelProperty(value = "审批人")
    private String approveName;

    /**
     * 审批人头像
     */
    @ApiModelProperty(value = "审批人头像")
    private String approveByHead;

    /**
     * 审批时间
     */
    @ApiModelProperty(value = "审批时间")
    private LocalDateTime approveTime;

    /**
     * 审批意见
     */
    @ApiModelProperty(value = "审批意见")
    private String approveOpinion;

    /**
     * 学生id
     */
    @ApiModelProperty(value = "学生id")
    private Long studentId;
}
