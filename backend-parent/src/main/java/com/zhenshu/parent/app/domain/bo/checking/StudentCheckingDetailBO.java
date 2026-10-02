package com.zhenshu.parent.app.domain.bo.checking;

import com.zhenshu.parent.common.constant.enums.StudentCheckingStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-22
 * @desc 学生出勤详情查询 出参
 */
@Data
@ApiModel(description = "学生出勤详情查询出参")
public class StudentCheckingDetailBO {

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
     * 出勤状态 0病假 1事假 2在园
     */
    @ApiModelProperty(value = "出勤状态 0病假 1事假 2在园")
    private StudentCheckingStatus status;

    /**
     * 上午体温
     */
    @ApiModelProperty(value = "上午体温")
    private Double amTemperature;

    /**
     * 上午测量时间
     */
    @ApiModelProperty(value = "上午测量时间")
    private String amMeasureTime;

    /**
     * 下午体温
     */
    @ApiModelProperty(value = "下午体温")
    private Double pmTemperature;

    /**
     * 下午测量时间
     */
    @ApiModelProperty(value = "下午测量时间")
    private String pmMeasureTime;
}
