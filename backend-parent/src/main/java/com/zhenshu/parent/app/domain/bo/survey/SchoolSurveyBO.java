package com.zhenshu.parent.app.domain.bo.survey;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zch
 * @version 1.0
 * @desc 学校概况 出参
 * @date 2022-06-24
 **/
@Data
@ApiModel(description = "学校概况 出参")
public class SchoolSurveyBO {
    /**
     * 图片
     */
    @ApiModelProperty("图片")
    private String imgUrl;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String surveyContent;
}
