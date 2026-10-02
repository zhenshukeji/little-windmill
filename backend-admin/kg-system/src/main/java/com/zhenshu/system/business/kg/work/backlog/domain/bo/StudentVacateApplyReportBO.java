package com.zhenshu.system.business.kg.work.backlog.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author zch
 * @date 2022-06-14
 * @version 1.0
 * @desc 学生月度请假日数出参
 */

@Data
@ApiModel(description = "学生月度请假日数")
public class StudentVacateApplyReportBO {

    /**
     * 学生id
     */
    @ApiModelProperty(value = "学生id")
    private Long studentId;

    /**
     * 请假开始日期
     */
    @ApiModelProperty(value = "请假开始时间")
    private LocalDate beginTime;

    /**
     * 请假结束日期
     */
    @ApiModelProperty(value = "请假结束时间")
    private LocalDate endTime;
}
