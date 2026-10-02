package com.zhenshu.system.business.kg.work.backlog.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.enums.kg.work.backlog.ApplyStatus;
import com.zhenshu.common.enums.kg.work.backlog.VacateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * @author xxx
 * @version 1.0
 * @date 2022-03-07
 * @desc 学生请假申请表 详情出参
 */
@Data
@ApiModel(description = "学生请假申请表 详情出参")
public class StudentVacateApplyDetailsBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 请假类型 0病假 1事假
     */
    @ApiModelProperty(value = "请假类型 0病假 1事假")
    private VacateType type;

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
     * 请假原因
     */
    @ApiModelProperty(value = "请假原因")
    private String reason;

    /**
     * 审批人id
     */
    @ApiModelProperty(value = "审批人id")
    private Long approveBy;

    /**
     * 审批人姓名
     */
    @ApiModelProperty(value = "审批人姓名")
    private String approveName;

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
     * 申请状态 0待处理 1已同意 2未同意 3已撤回
     */
    @ApiModelProperty(value = "申请状态 0待处理 1已同意 2未同意 3已撤回")
    private ApplyStatus status;

    /**
     * 校区Id
     */
    @ApiModelProperty(value = "校区Id")
    private Long kgId;

    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    private LocalDateTime applyTime;

    /**
     * 学生id
     */
    @ApiModelProperty(value = "学生id")
    private Long studentId;

    /**
     * 学生姓名
     */
    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    /**
     * 班级名称
     */
    @ApiModelProperty(value = "班级名称")
    private String className;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    private LocalDate birthdate;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private String age;

    /**
     * 申请人手机号
     */
    @ApiModelProperty(value = "申请人手机号")
    private String applyPhone;

    /**
     * 申请人姓名
     */
    @ApiModelProperty(value = "申请人姓名")
    private String applyName;
}
