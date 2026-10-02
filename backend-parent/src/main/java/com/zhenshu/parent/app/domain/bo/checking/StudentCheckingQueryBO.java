package com.zhenshu.parent.app.domain.bo.checking;

import com.zhenshu.parent.app.domain.dto.checking.StudentCheckingData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-22
 * @desc 学生出勤查询 出参
 */
@Data
@ApiModel(description = "学生出勤查询出参")
public class StudentCheckingQueryBO {
    /**
     * 学生出勤信息
     */
    @ApiModelProperty(value = "学生出勤信息")
    private List<StudentCheckingData> studentCheckingData;

    /**
     * 在园数量
     */
    @ApiModelProperty(value = "在园数量")
    private Integer atSchoolCount;

    /**
     * 缺勤数量
     */
    @ApiModelProperty(value = "缺勤数量")
    private Integer absenceCount;

    /**
     * 请假数量
     */
    @ApiModelProperty(value = "请假数量")
    private Integer vacateCount;
}
