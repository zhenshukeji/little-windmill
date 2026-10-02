package com.zhenshu.system.business.kg.base.advanced.domain.bo;

import com.zhenshu.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-03-01
 * @desc 园区概况表 出参
 */
@Data
@ApiModel(description = "园区概况表 出参")
public class SchoolSurveyBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 园区概况ID
     */
    @Excel(name = "园区概况ID")
    @ApiModelProperty(value = "园区概况ID")
    private Long id;

    /**
     * 图片url
     */
    @Excel(name = "图片url")
    @ApiModelProperty(value = "图片url")
    private String imgUrl;

    /**
     * 内容
     */
    @Excel(name = "内容")
    @ApiModelProperty(value = "内容")
    private String surveyContent;

    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

}
