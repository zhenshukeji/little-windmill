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
 * @author xxx
 * @version 1.0
 * @date 2022-02-16
 * @desc 年级表 修改入参
 */
@Data
@ApiModel(description = "年级表 修改入参")
public class GradeEditVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 年级id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "年级id")
    private Long id;

    /**
     * 年级名
     */
    @Xss
    @Size(max = 50)
    @NotEmpty
    @ApiModelProperty(required = true, value = "年级名")
    private String gradeName;

}
