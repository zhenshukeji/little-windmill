package com.zhenshu.system.business.bloc.base.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-11
 * @desc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询园区表 入参")
public class KindergartenQueryVO extends PageEntity {
    /**
     * 学校名称
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(value = "学校名称")
    private String kindergartenName;
}
