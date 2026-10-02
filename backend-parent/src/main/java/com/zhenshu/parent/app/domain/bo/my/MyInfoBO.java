package com.zhenshu.parent.app.domain.bo.my;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/27 17:41
 * @desc 我的资料出参
 */
@Data
@ApiModel(description = "我的资料出参")
public class MyInfoBO {
    /**
     * 学生id
     */
    @ApiModelProperty("学生id")
    private Long studentId;

    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String studentName;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String headImg;

    /**
     * 性别 0男 1女 2未知
     */
    @ApiModelProperty(value = "性别 0男 1女 2未知")
    private Integer gander;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    private LocalDate birthdate;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private String age;

    /**
     * 家庭地址
     */
    @ApiModelProperty(value = "家庭地址")
    private String address;

    /**
     * 监护人
     */
    @ApiModelProperty(value = "监护人")
    private List<GuardianBO> guardians;
}
