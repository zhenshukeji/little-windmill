package com.zhenshu.system.business.ruoyi.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.annotation.Excel;
import com.zhenshu.common.annotation.Excel.ColumnType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/5/6 15:01
 * @desc 日志出参
 */
@Data
@ApiModel(description = "日志出参")
public class LogBO {
    /**
     * 日志主键
     */
    @ApiModelProperty(value = "日志主键")
    @Excel(name = "操作序号", cellType = ColumnType.NUMERIC)
    private Long operId;

    /**
     * 操作地址
     */
    @ApiModelProperty(value = "操作地址")
    @Excel(name = "操作地址")
    private String operIp;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

    /**
     * 操作模块
     */
    @ApiModelProperty(value = "操作模块")
    @Excel(name = "操作模块")
    private String module;

    /**
     * 操作菜单
     */
    @ApiModelProperty(value = "操作菜单")
    @Excel(name = "菜单")
    private String menu;

    /**
     * 操作功能
     */
    @ApiModelProperty(value = "操作功能")
    @Excel(name = "操作功能")
    private String function;

    /**
     * 操作人员
     */
    @ApiModelProperty(value = "操作人员")
    @Excel(name = "操作人员")
    private String nickName;

    /**
     * 操作状态（0正常 1异常）
     */
    @ApiModelProperty(value = "操作状态（0正常 1异常）")
    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    private Integer status;
}
