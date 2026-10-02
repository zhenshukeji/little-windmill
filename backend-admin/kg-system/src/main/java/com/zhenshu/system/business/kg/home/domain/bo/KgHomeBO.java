package com.zhenshu.system.business.kg.home.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/9 18:46
 * @desc 首页出参
 */
@Data
@ApiModel(description = "首页出参")
public class KgHomeBO {
    /**
     * 学生请假审批（社区版仅保留此项）
     */
    @ApiModelProperty("学生请假审批")
    private KgHomePendingBO studentVacate;
}
