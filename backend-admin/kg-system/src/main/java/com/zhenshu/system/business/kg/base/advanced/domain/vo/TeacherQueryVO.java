package com.zhenshu.system.business.kg.base.advanced.domain.vo;


import com.zhenshu.common.domain.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-28
 * @desc 查询可绑定老师入参
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询可绑定老师入参")
public class TeacherQueryVO extends PageEntity {
    /**
     * 老师姓名
     */
    @ApiModelProperty(value = "老师姓名")
    private String name;
}
