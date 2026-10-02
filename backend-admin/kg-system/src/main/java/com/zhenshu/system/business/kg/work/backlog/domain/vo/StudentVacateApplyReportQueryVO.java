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
 * @desc 查询学生月份请假同意部分信息 入参
 */
@Data
@ApiModel(description = "查询学生月份请假同意部分信息 入参")
public class StudentVacateApplyReportQueryVO {
    /**
     * 月份第一天
     */
    @ApiModelProperty(value = "月份第一天", hidden = true)
    private LocalDate first;

    /**
     * 月份最后一天
     */
    @ApiModelProperty(value = "月份最后一天", hidden = true)
    private LocalDate last;

    /**
     * 学生id
     */
    @ApiModelProperty(value = "学生id", hidden = true)
    private List<Long> studentIds;

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
