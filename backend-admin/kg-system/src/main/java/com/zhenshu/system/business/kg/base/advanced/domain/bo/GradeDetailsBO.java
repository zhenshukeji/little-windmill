package com.zhenshu.system.business.kg.base.advanced.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-16
 * @desc 年级表 详情出参
 */
@Data
@ApiModel(description = "年级表 详情出参")
public class GradeDetailsBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 年级名
     */
    @ApiModelProperty(value = "年级名")
    private String gradeName;

    /**
     * 校区Id
     */
    @ApiModelProperty(value = "校区Id")
    private Long kgId;
}
