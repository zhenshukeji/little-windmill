package com.zhenshu.system.business.kg.base.advanced.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-03-01
 * @desc 学期表 修改入参
 */
@Data
@ApiModel(description = "学期表 修改入参")
public class SemesterEditVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学期ID
     */
    @NotNull
    @ApiModelProperty(required = true, value = "学期ID")
    private Long id;

    /**
     * 学期名称
     */
    @Xss
    @Size(max = 50)
    @NotEmpty
    @ApiModelProperty(required = true, value = "学期名称")
    private String semesterName;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @ApiModelProperty(required = true, value = "开始日期")
    private Date beginDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @ApiModelProperty(required = true, value = "结束日期")
    private Date endDate;

}
