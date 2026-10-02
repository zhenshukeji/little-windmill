package com.zhenshu.system.business.kg.base.advanced.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-03-01
 * @desc 学期表 详情出参
 */
@Data
@ApiModel(description = "学期表 详情出参")
public class SemesterDetailsBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学期名称
     */
    @ApiModelProperty(value = "学期名称")
    private String semesterName;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始日期")
    private Date beginDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期")
    private Date endDate;

    /**
     * 校区Id
     */
    @ApiModelProperty(value = "校区Id")
    private Long kgId;
}
