package com.zhenshu.parent.app.domain.bo.checking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zch
 * @version 1.0
 * @data 2022-07-19
 * @desc 获取出勤学生信息
 */
@Data
public class CheckingStudentDataBO {
    /**
     * 学生姓名
     */
    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    /**
     * 校区名称
     */
    @ApiModelProperty(value = "校区名称")
    private String kindergartenName;

    /**
     * 班级名称
     */
    @ApiModelProperty(value = "班级名称")
    private String className;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String imgUrl;
}
