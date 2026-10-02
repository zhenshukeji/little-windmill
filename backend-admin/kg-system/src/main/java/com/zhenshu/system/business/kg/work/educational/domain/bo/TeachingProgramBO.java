package com.zhenshu.system.business.kg.work.educational.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-09
 * @desc 教学计划
 */
@Data
@ApiModel(description = "教学计划出参")
public class TeachingProgramBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 班级名称
     */
    @Excel(name = "教学计划id")
    @ApiModelProperty(value = "教学计划id")
    private Long id;
    /**
     * 班级名称
     */
    @Excel(name = "班级名称")
    @ApiModelProperty(value = "班级名称")
    private String className;
    /**
     * 教学计划图片
     */
    @Excel(name = "教学计划图片地址")
    @ApiModelProperty(value = "教学计划图片地址")
    private String planUrl;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间")
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginDate;

    /**
     * 员工姓名
     */
    @Excel(name = "员工姓名")
    @ApiModelProperty(value = "员工姓名")
    private String name;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间")
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endDate;
}
