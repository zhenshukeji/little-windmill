package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import com.zhenshu.common.enums.kg.base.record.KgStaffQueryType;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-15
 * @desc 查询学校员工表 入参
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询学校员工入参")
public class KindergartenStaffQueryVO extends PageEntity {
    /**
     * 姓名
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "姓名")
    private String name;

    /**
     * 手机号码
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "手机号码")
    private String phone;

    /**
     * 查询类型
     */
    @NotNull
    @ApiModelProperty(required = true, value = "查询类型")
    private KgStaffQueryType queryType;
}
