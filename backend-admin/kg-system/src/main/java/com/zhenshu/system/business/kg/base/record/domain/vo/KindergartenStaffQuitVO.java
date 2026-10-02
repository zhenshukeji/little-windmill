package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-15
 * @desc 学校员工离职入参
 */
@Data
@ApiModel(description = "学校员工离职入参")
public class KindergartenStaffQuitVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "id")
    private Long id;

    /**
     * 离职时间
     */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(required = true, value = "离职时间")
    private Date quitDate;

    /**
     * 离职原因
     */
    @Size(max = 100)
    @ApiModelProperty(required = true, value = "离职原因")
    private String quitReason;
}
