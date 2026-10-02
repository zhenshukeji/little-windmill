package com.zhenshu.parent.app.domain.bo.circle;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author zch
 * @version 1.0
 * @desc 班级圈 出参
 * @date 2022-06-29
 **/
@Data
@ApiModel(description = "班级圈 出参")
public class ClassCircleBO {
    /**
     * 创建人头像
     */
    @ApiModelProperty("创建人头像")
    private String avatar;
    /**
     * 创建人姓名
     */
    @ApiModelProperty("创建人姓名")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDate createTime;

    /**
     * 发布的文字
     */
    @ApiModelProperty("发布的文字")
    private String textContent;

    /**
     * 上传附件类型(0 文件 1视频)
     */
    @ApiModelProperty("上传附件类型(0 文件 1视频)")
    private Integer uploadType;

    /**
     * 附件地址
     */
    @ApiModelProperty("附件地址")
    private String attachUrl;

}
