package com.zhenshu.system.business.kg.base.record.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/23 15:52
 * @desc 学生返校重读入参
 */
@Data
@ApiModel(description = "学生返校重读入参")
public class StudentBackToSchoolVO {
    /**
     * 学生id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "学生id")
    private List<Long> studentIds;

    /**
     * 班级id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "班级id")
    private Long classId;
}
