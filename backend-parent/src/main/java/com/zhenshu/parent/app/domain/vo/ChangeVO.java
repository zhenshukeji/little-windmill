package com.zhenshu.parent.app.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jing
 * @version 1.0
 * @desc 选择小朋友入参
 * @date 2022/5/24 0024 17:37
 **/
@Data
@ApiModel
public class ChangeVO {

    @NotNull
    @ApiModelProperty("学生id")
    private Long studentId;
}
