package com.zhenshu.system.business.kg.base.record.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/25 14:16
 * @desc 学生人脸出参
 */
@Data
@ApiModel(description = "学生人脸出参")
public class StudentFaceBO {
    /**
     * 学生id
     */
    @ApiModelProperty(value = "学生id")
    private Long id;

    /**
     * 图片url
     */
    @ApiModelProperty(value = "图片url")
    private String realImgUrl;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}
