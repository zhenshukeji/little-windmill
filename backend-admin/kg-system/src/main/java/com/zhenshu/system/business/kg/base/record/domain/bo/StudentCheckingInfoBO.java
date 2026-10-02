package com.zhenshu.system.business.kg.base.record.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/7/5 15:19
 * @desc 学生出勤信息出参
 */
@Data
@ApiModel(description = "学生出勤信息出参")
public class StudentCheckingInfoBO {
    /**
     * 学生ID
     */
    @ApiModelProperty(value = "学生ID")
    private Long studentId;

    /**
     * 学生姓名
     */
    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    /**
     * 体温
     */
    @ApiModelProperty(value = "体温")
    private Double temperature;

    /**
     * 测量时间
     */
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    @ApiModelProperty(value = "测量时间")
    private LocalDateTime time;
}
