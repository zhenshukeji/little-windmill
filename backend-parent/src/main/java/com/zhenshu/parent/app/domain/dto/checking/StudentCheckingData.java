package com.zhenshu.parent.app.domain.dto.checking;

import com.zhenshu.parent.common.constant.enums.StudentCheckingStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author zch
 * @version 1.0
 * @date 2022-07-19
 * @desc 学生出勤出参属性
 */
@Data
public class StudentCheckingData {
    /**
     * 出勤日期
     */
    @ApiModelProperty(value = "出勤日期")
    private LocalDate checkingDate;

    /**
     * 出勤状态
     */
    @ApiModelProperty(value = "出勤状态")
    private StudentCheckingStatus status;
}
