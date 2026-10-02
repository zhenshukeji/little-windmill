package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 查询学生入参
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询学生入参")
public class StudentQueryVO extends PageEntity {
    /**
     * 信息
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "信息")
    private String info;

    /**
     * 班级ID
     */
    @ApiModelProperty(required = false, value = "班级ID")
    private Long classId;

    /**
     * 是否高位体弱
     */
    @ApiModelProperty(required = false, value = "是否高位体弱")
    private Boolean isWeak;

    /**
     * 是否录入人脸
     */
    @ApiModelProperty(required = false, value = "是否录入人脸")
    private Boolean hasFace;

}
