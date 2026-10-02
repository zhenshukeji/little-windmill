package com.zhenshu.parent.app.domain.vo.checking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author zch
 * @version 1.0
 * @desc 学生出勤 入参
 * @date 2022-06-22
 **/
@Data
public class StudentCheckingQueryVO {
    /**
     * 出勤年月
     */
    @NotNull
    @ApiModelProperty("出勤年月")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkingDate;
}
