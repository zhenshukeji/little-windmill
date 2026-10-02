package com.zhenshu.system.business.kg.base.advanced.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-03-01
 * @desc 园区概况表 详情出参
 */
@Data
@ApiModel(description = "园区概况表 详情出参")
public class SchoolSurveyDetailsBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 图片url
     */
    @ApiModelProperty(value = "图片url")
    private String imgUrl;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String surveyContent;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String modifier;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
}
