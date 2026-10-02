package com.zhenshu.system.business.kg.home.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/10 9:45
 * @desc 待办事项出参
 */
@Data
@ApiModel(description = "待办事项出参")
public class KgHomePendingItemBO {
    /**
     * 关联的id
     */
    @ApiModelProperty(value = "关联的id")
    private Long id;

    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    private LocalDateTime applyTime;

    /**
     * 内容描述
     */
    @ApiModelProperty(value = "内容描述")
    private String describe;

    /**
     * 班级名称
     */
    @ApiModelProperty(value = "班级名称")
    private String className;

    /**
     * 申请人
     */
    @ApiModelProperty(value = "申请人")
    private String applyName;
}
