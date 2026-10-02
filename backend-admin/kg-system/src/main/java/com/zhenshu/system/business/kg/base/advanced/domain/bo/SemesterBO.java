package com.zhenshu.system.business.kg.base.advanced.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-03-01
 * @desc 学期表 出参
 */
@Data
@ApiModel(description = "学期表 出参")
public class SemesterBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学期名称
     */
    @Excel(name = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 学期名称
     */
    @Excel(name = "学期名称")
    @ApiModelProperty(value = "学期名称")
    private String semesterName;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始日期")
    private Date beginDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期")
    private Date endDate;

}
