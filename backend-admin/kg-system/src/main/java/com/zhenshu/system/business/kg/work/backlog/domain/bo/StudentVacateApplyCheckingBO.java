package com.zhenshu.system.business.kg.work.backlog.domain.bo;

import com.zhenshu.common.enums.kg.work.backlog.VacateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * @author zch
 * @version 1.0
 * @date 2022-05-19
 * @desc 学生请假申请考勤出参
 */
@Data
@ApiModel(description = "学生请假申请考勤出参")
public class StudentVacateApplyCheckingBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请假id
     */
    @ApiModelProperty(value = "请假学生id")
    private Long studentId;

    /**
     * 请假类型
     */
    @ApiModelProperty(value = "请假类型")
    private VacateType type;

    /**
     * 请假时间
     */
    @ApiModelProperty(value = "请假时间")
    private LocalDate beginTime;

    /**
     * 请假时间
     */
    @ApiModelProperty(value = "请假时间")
    private LocalDate endTime;
}
