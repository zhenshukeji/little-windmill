package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 查询历史学生入参
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询历史学生入参")
public class StudentQueryHistoryVO extends PageEntity {
    /**
     * 身份证号码
     */
    @Xss
    @Size(max = 25)
    @ApiModelProperty(required = false, value = "身份证号码")
    private String identityNumber;

    /**
     * 信息
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "信息")
    private String info;

    /**
     * 查询类型
     */
    @NotNull
    @Range(min = 0, max = 1)
    @ApiModelProperty(required = true, value = "查询类型 0:只有identityNumber生效 1:只有info生效")
    private Integer type;
}
