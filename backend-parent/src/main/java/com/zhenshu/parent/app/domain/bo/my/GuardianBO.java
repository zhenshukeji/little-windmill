package com.zhenshu.parent.app.domain.bo.my;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/27 17:43
 * @desc 监护人出参
 */
@Data
@ApiModel(description = "监护人出参")
public class GuardianBO {
    /**
     * 监护人id
     */
    @ApiModelProperty(value = "监护人id")
    private Long id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 关系
     */
    @ApiModelProperty(value = "关系")
    private String relation;
}
