package com.zhenshu.parent.app.domain.bo.program;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zch
 * @version 1.0
 * @desc 教学计划
 * @date 2022-06-21
 **/
@Data
@ApiModel
public class TeachingProgramBO {
    /**
     * 教学计划id
     */
    @ApiModelProperty("教学计划id")
    private Long id;

    /**
     * 教学计划照片
     */
    @ApiModelProperty("教学计划照片")
    private String planUrl;

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
     * 创建多长时间时间
     */
    @ApiModelProperty("创建多长时间时间")
    private String createTime;

    /**
     * 更新多长时间
     */
    @ApiModelProperty("更新多长时间")
    private String updateTime;


}
