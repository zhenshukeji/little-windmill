package com.zhenshu.system.business.kg.work.backlog.domain.vo;

import com.zhenshu.common.enums.kg.work.backlog.ApplyStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


/**
 * @author zch
 * @version 1.0
 * @date 2022-06-15
 * @desc 查询日期范围内学生请假申请表 入参
 */
@Data
@ApiModel(description = "查询日期范围内学生请假申请表 入参")
public class StudentVacateApplyDateRangeQueryVO {
    /**
     * 学生ids
     */
    @ApiModelProperty(value = "学生ids")
    private List<Long> studentIds;

    /**
     * 开始日期
     */
    @ApiModelProperty(value = "开始日期", hidden = true)
    private LocalDate minDate;

    /**
     * 结束日期
     */
    @ApiModelProperty(value = "结束日期", hidden = true)
    private LocalDate maxDate;

    /**
     * 申请状态
     */
    @ApiModelProperty(value = "申请状态", hidden = true)
    private ApplyStatus status;

    /**
     * 校区ID, 前端不用传递; 为了写MyBatis查询语句的时候方便取值;
     */
    @ApiModelProperty(value = "校区ID, 不用传递", hidden = true)
    private Long kgId;

}
