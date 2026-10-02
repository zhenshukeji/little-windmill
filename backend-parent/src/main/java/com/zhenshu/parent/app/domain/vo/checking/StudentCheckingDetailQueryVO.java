package com.zhenshu.parent.app.domain.vo.checking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author zch
 * @version 1.0
 * @desc 学生出勤详情 入参
 * @date 2022-06-22
 **/
@Data
public class StudentCheckingDetailQueryVO {

    /**
     * 出勤日期
     */
    @NonNull
    @ApiModelProperty("出勤日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkingDate;
}
