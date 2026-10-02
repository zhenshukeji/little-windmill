package com.zhenshu.system.business.kg.work.educational.domain.bo;

import com.zhenshu.common.enums.kg.work.educational.CircleUploadType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * @author zch
 * @version 1.0
 * @date 2022-05-11
 * @desc 详情出参
 */
@Data
@ApiModel(description = "详情出参")
public class ClassCircleDetailsBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发布的文字
     */
    @ApiModelProperty(value = "发布的文字")
    private String textContent;

    /**
     * 上传附件类型(0 文件 1视频)
     */
    @ApiModelProperty(value = "上传附件类型(0 文件 1视频)")
    private CircleUploadType uploadType;

    /**
     * 校区Id
     */
    @ApiModelProperty(value = "校区Id")
    private Long kgId;
}
