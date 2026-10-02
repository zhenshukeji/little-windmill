package com.zhenshu.parent.app.domain.bo.checking;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-22
 * @desc 学生出勤查询 出参
 */
@Data
@ApiModel(description = "学生出勤查询出参")
public class StudentVacateApplyQueryBO {

    /**
     * 请假开始时间
     */
    @ApiModelProperty(value = "请假开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginTime;

    /**
     * 请假结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "请假结束时间")
    private LocalDate endTime;

    /**
     * 出勤状态
     */
    @ApiModelProperty(value = "出勤状态")
    private Integer vacateType;
}
