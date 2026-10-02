package com.zhenshu.parent.app.domain.bo.program;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author zch
 * @version 1.0
 * @desc 教学计划
 * @date 2022-06-21
 **/
@Data
@ApiModel
public class TeachingProgramDetailBO {

    @ApiModelProperty("班级名称")
    private String className;

    /**
     * 教学计划照片
     */
    @ApiModelProperty("教学计划照片")
    private String planUrl;

    /**
     * 创建时间
     */
    @JsonIgnore
    @ApiModelProperty("创建时间")
    private LocalDate beginDate;

    /**
     * 教学计划时间
     */
    @ApiModelProperty("教学计划时间")
    private String date;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDate createTime;


}
