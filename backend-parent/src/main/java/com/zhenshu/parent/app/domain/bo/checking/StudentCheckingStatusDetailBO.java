package com.zhenshu.parent.app.domain.bo.checking;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-22
 * @desc 学生出勤详情查询 出参
 */
@Data
@ApiModel(description = "学生出勤详情查询出参")
public class StudentCheckingStatusDetailBO {
    /**
     * 请假详情
     */
    @ApiModelProperty(value = "请假详情")
    StudentVacateApplyDetailBO applyDetail;
    /**
     * 出勤详情
     */
    @ApiModelProperty(value = "出勤详情")
    StudentCheckingDetailBO checkingDetail;
}
