package com.zhenshu.system.business.kg.work.educational.domain.vo;

import com.zhenshu.common.enums.kg.work.educational.CircleUploadType;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * @author zch
 * @version 1.0
 * @date 2022-05-11
 * @desc 修改入参
 */
@Data
@ApiModel(description = "修改入参")
public class ClassCircleEditVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级圈id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "班级圈id")
    private Long id;

    /**
     * 发布的文字
     */
    @Xss
    @Size(max = 255)
    @NotEmpty
    @ApiModelProperty(required = true, value = "发布的文字")
    private String textContent;

    /**
     * 上传附件类型(0 文件 1视频)
     */
    @NotNull
    @ApiModelProperty(required = true, value = "上传附件类型(0 文件 1视频)")
    private CircleUploadType uploadType;

}
