package com.zhenshu.system.business.kg.work.educational.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author zch
 * @version 1.0
 * @date 2022-05-11
 * @desc 删除入参
 */
@Data
@ApiModel(description = "删除入参")
public class ClassCircleDeleteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级圈id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "班级圈id")
    private Long id;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者", hidden = true)
    private Long createBy;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新者", hidden = true)
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", hidden = true)
    private LocalDateTime updateTime;
}
