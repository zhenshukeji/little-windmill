package com.zhenshu.parent.app.domain.bo.my;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/27 18:18
 * @desc 我的出参
 */
@Data
@ApiModel(description = "我的出参")
public class MyBO {
    /**
     * 学生姓名
     */
    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    /**
     * 校区名称
     */
    @ApiModelProperty(value = "校区名称")
    private String kgName;

    /**
     * 班级名称
     */
    @ApiModelProperty(value = "班级名称")
    private String className;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private String age;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String imgUrl;
}
