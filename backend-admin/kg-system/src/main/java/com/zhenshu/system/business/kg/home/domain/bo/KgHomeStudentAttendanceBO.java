package com.zhenshu.system.business.kg.home.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/9 18:51
 * @desc 学生出勤
 */
@Data
@ApiModel(description = "学生出勤")
public class KgHomeStudentAttendanceBO {
    /**
     * 出勤人数
     */
    @ApiModelProperty(value = "出勤人数")
    private Integer count;

    /**
     * 出勤率
     */
    @ApiModelProperty(value = "出勤率")
    private BigDecimal rate;
}
