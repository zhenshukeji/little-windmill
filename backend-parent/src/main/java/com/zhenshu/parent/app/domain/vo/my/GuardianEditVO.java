package com.zhenshu.parent.app.domain.vo.my;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/27 17:43
 * @desc 监护人出参
 */
@Data
@ApiModel(description = "监护人出参")
public class GuardianEditVO {
    /**
     * 监护人id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "监护人id")
    private Long id;

    /**
     * 姓名
     */
    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(required = true, value = "姓名")
    private String name;

    /**
     * 手机号
     */
    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(required = true, value = "手机号")
    private String phone;

    /**
     * 关系
     */
    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(required = true, value = "关系")
    private String relation;
}
