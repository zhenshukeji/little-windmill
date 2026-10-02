package com.zhenshu.system.business.kg.home.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/10 13:38
 * @desc 班级考勤出参
 */
@Data
@ApiModel(description = "班级考勤出参")
public class KgHomeClassAttendanceDateBO {
    /**
     * 出勤人数
     */
    @ApiModelProperty(value = "出勤人数")
    private Integer attendanceCount;

    /**
     * 缺勤人数
     */
    @ApiModelProperty(value = "缺勤人数")
    private Integer absenceCount;

    /**
     * 出勤率
     */
    @ApiModelProperty(value = "出勤率")
    private BigDecimal attendanceRate;

    /**
     * 缺勤率
     */
    @ApiModelProperty(value = "缺勤率")
    private BigDecimal absenceRate;
}
