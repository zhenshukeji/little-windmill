package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.zhenshu.common.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/7/5 17:44
 * @desc 日历入参
 */
@Data
@ApiModel(description = "日历入参")
public class CalendarVO {
    /**
     * 日期
     */
    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    @ApiModelProperty(required = true, value = "日期 dd部分没有要求, 只要是个正确的日期就行")
    private LocalDate date;

    /**
     * 学生id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "学生id")
    private Long studentId;
}
