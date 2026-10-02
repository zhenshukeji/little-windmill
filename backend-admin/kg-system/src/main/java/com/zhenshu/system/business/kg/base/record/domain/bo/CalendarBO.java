package com.zhenshu.system.business.kg.base.record.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/7/5 17:40
 * @desc 日历出参
 */
@Data
@ApiModel(description = "日历出参")
public class CalendarBO {
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private Integer day;

    /**
     * 是否休息
     */
    @ApiModelProperty(value = "是否休息")
    private Boolean isRest;

    /**
     * 是否有考勤记录
     */
    @ApiModelProperty(value = "是否有考勤记录")
    private Boolean hasChecking;
}
