package com.zhenshu.system.business.kg.base.advanced.domain.bo;

import com.zhenshu.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-16
 * @desc 年级表 出参
 */
@Data
@ApiModel(description = "年级表 出参")
public class GradeBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Excel(name = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 年级名
     */
    @Excel(name = "年级名")
    @ApiModelProperty(value = "年级名")
    private String gradeName;

}
