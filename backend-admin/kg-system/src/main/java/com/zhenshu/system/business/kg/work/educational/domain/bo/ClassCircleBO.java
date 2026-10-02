package com.zhenshu.system.business.kg.work.educational.domain.bo;

import com.zhenshu.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author zch
 * @version 1.0
 * @date 2022-05-11
 * @desc 出参
 */
@Data
@ApiModel(description = "出参")
public class ClassCircleBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级圈动态id
     */
    @Excel(name = "班级圈动态id")
    @ApiModelProperty(value = "班级圈动态id")
    private Long id;

    /**
     * 发布的文字
     */
    @Excel(name = "发布的文字")
    @ApiModelProperty(value = "发布的文字")
    private String textContent;

    /**
     * 上传附件类型(0 文件 1视频)
     */
    @Excel(name = "上传附件类型(0 文件 1视频)")
    @ApiModelProperty(value = "上传附件类型(0 文件 1视频)")
    private Integer uploadType;

    /**
     * 发布的文字
     */
    @Excel(name = "创建时间")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 创建人姓名
     */
    @Excel(name = "创建人姓名")
    @ApiModelProperty(value = "创建人姓名")
    private String username;
    /**
     * 岗位名称
     */
    @Excel(name = "岗位名称")
    @ApiModelProperty(value = "岗位名称")
    private String postName;
    /**
     * 上传文件list
     */
    @Excel(name = "上传视频")
    @ApiModelProperty(value = "上传视频")
    private String videoUrl;
    /**
     * 上传文件list
     */
    @Excel(name = "上传图片列表")
    @ApiModelProperty(value = "上传图片列表")
    private List<String> imgUrlList;

    /**
     * 上传文件list
     */
    @Excel(name = "上传图片多久")
    @ApiModelProperty(value = "上传图片多久")
    private String howLongCreate;

    /**
     * 上传文件list
     */
    @Excel(name = "上传图片或视频地址")
    @ApiModelProperty(value = "上传图片或视频地址")
    private String attachUrl;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    @ApiModelProperty(value = "创建人")
    private Long createBy;

    /**
     * 校区id
     */
    @Excel(name = "校区id")
    @ApiModelProperty(value = "校区id")
    private Long kgId;

}
