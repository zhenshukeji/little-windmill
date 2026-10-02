package com.zhenshu.system.business.bloc.base.domain.vo;

import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-11
 * @desc 修改入参
 */
@Data
@ApiModel(description = "园区表 修改入参")
public class KindergartenEditVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学校id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "学校id")
    private Long id;

    /**
     * 学校名称
     */
    @Xss
    @Size(max = 50)
    @NotEmpty
    @ApiModelProperty(required = true, value = "学校名称")
    private String kindergartenName;

    /**
     * 负责人姓名
     */
    @Xss
    @Size(max = 30)
    @NotEmpty
    @ApiModelProperty(required = true, value = "负责人姓名")
    private String principalName;

    /**
     * 负责人联系电话
     */
    @Xss
    @Size(max = 50)
    @NotEmpty
    @ApiModelProperty(required = true, value = "负责人联系电话")
    private String principalPhone;

    /**
     * 学校地址
     */
    @Xss
    @Size(max = 100)
    @NotEmpty
    @ApiModelProperty(required = true, value = "学校地址")
    private String kindergartenAddress;

    /**
     * 备注
     */
    @Xss
    @Size(max = 100)
    @ApiModelProperty(value = "备注")
    private String remark;
}
