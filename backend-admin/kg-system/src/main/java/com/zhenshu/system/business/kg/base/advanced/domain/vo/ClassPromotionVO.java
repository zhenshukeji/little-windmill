package com.zhenshu.system.business.kg.base.advanced.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/28
 * @desc 班级升班入参
 */
@Data
@ApiModel(description = "班级升班入参")
public class ClassPromotionVO {
    /**
     * 被升班的班级id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "被升班的班级id")
    private Long oldClassId;

    /**
     * 新班级id
     */
    @ApiModelProperty(required = false, value = "新班级id")
    private Long newClassId;

    /**
     * 是否毕业
     */
    @NotNull
    @ApiModelProperty(required = true, value = "是否毕业; 此值为false时, 其他值都为必填; 此值为true时, newClassId、teacherId将失效;")
    private Boolean isGraduate;

    /**
     * 新班主任id
     */
    @ApiModelProperty(required = false, value = "新班主任id")
    private Long teacherId;
}
