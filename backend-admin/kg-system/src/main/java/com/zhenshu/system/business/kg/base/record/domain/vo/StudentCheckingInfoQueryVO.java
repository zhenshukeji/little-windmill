package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.zhenshu.common.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/7/5 15:22
 * @desc
 */
@Data
@ApiModel(description = "学生出勤信息查询入参")
public class StudentCheckingInfoQueryVO {
    /**
     * 学生id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "学生id")
    private Long studentId;

    /**
     * 日期
     */
    @NotNull
    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    @ApiModelProperty(required = true, value = "日期")
    private LocalDate date;
}
