package com.zhenshu.system.business.kg.base.advanced.domain.vo;

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
 * @date 2022-03-01
 * @desc 园区概况表 修改入参
 */
@Data
@ApiModel(description = "园区概况表 修改入参")
public class SchoolSurveyEditVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 园区概况ID
     */
    @NotNull
    @ApiModelProperty(required = true, value = "园区概况ID")
    private Long id;

    /**
     * 图片url
     */
    @Xss
    @Size(max = 200)
    @NotEmpty
    @ApiModelProperty(required = true, value = "图片url")
    private String imgUrl;

    /**
     * 内容
     */
    @Size(max = 600)
    @NotEmpty
    @ApiModelProperty(required = true, value = "内容")
    private String surveyContent;

}
