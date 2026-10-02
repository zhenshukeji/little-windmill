package com.zhenshu.system.business.kg.work.backlog.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.annotation.Excel;
import com.zhenshu.common.enums.kg.work.backlog.ApplyStatus;
import com.zhenshu.common.enums.kg.work.backlog.VacateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author xyh
 * @version 1.0
 * @date 2022-03-07
 * @desc 学生请假申请出参
 */
@Data
@ApiModel(description = "学生请假申请出参")
public class StudentVacateApplyBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请假id
     */
    @Excel(name = "请假id")
    @ApiModelProperty(value = "请假id")
    private Long id;

    /**
     * 请假类型
     */
    @Excel(name = "请假类型")
    @ApiModelProperty(value = "请假类型")
    private VacateType type;

    /**
     * 请假开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "请假开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "请假开始时间")
    private LocalDateTime beginTime;

    /**
     * 请假结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "请假结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "请假结束时间")
    private LocalDateTime endTime;

    /**
     * 申请状态
     */
    @Excel(name = "申请状态")
    @ApiModelProperty(value = "申请状态")
    private ApplyStatus status;

    /**
     * 班级名
     */
    @Excel(name = "班级名")
    @ApiModelProperty(value = "班级名")
    private String className;

    /**
     * 学生id
     */
    @Excel(name = "学生id")
    @ApiModelProperty(value = "学生id")
    private Long studentId;

    /**
     * 学生姓名
     */
    @Excel(name = "学生姓名")
    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    /**
     * 申请人手机号
     */
    @Excel(name = "申请人手机号")
    @ApiModelProperty(value = "申请人手机号")
    private String phone;

    /**
     * 申请人
     */
    @Excel(name = "申请人")
    @ApiModelProperty(value = "申请人")
    private String applyName;

}
