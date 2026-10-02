package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/23 11:07
 * @desc 监护人新增入参
 */
@Data
@ApiModel(description = "监护人新增入参")
public class GuardianAddVO {
    /**
     * 姓名
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = true, value = "姓名")
    private String name;

    /**
     * 关系
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "关系")
    private String relation;

    /**
     * 电话号码
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = true, value = "电话号码")
    private String phone;

    /**
     * 职业
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "职业")
    private String job;

    /**
     * 证件类型
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "证件类型")
    private String cardType;

    /**
     * 证件号码
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "证件号码")
    private String cardNumber;
}
