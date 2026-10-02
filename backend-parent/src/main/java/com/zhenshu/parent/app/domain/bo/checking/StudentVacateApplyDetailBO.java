package com.zhenshu.parent.app.domain.bo.checking;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-22
 * @desc 学生请假详情查询 出参
 */
@Data
@ApiModel(description = "学生请假详情查询出参")
public class StudentVacateApplyDetailBO {

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
     * 请假类型 0病假 1事假
     */
    @ApiModelProperty(value = "请假类型 0病假 1事假")
    private String vacateType;

    /**
     * 请假时间
     */
    @ApiModelProperty(value = "请假时间")
    private String applyTime;
}
