package com.zhenshu.system.business.kg.base.advanced.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-16
 * @desc 查询年级表 入参
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询年级表 入参")
public class GradeQueryVO extends PageEntity {
    /**
     * 年级名
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "年级名")
    private String gradeName;

}
