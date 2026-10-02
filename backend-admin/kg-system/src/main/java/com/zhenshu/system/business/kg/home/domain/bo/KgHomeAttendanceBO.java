package com.zhenshu.system.business.kg.home.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/10 11:24
 * @desc 全校出勤人数
 */
@Data
@ApiModel(description = "全校出勤人数")
public class KgHomeAttendanceBO {
    /**
     * 出勤人数
     */
    @ApiModelProperty(value = "出勤人数")
    private Integer count;

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private LocalDate date;
}
