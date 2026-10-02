package com.zhenshu.parent.app.domain.bo.health;

import com.zhenshu.parent.app.domain.bo.my.GuardianBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/28 10:23
 * @desc 保健档案基本信息出参
 */
@Data
@ApiModel(description = "保健档案基本信息出参")
public class HealthBaseInfoBO {
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
     * 班级姓名
     */
    @ApiModelProperty("班级姓名")
    private String className;

    /**
     * 性别 0男 1女 2未知
     */
    @ApiModelProperty(value = "性别 0男 1女 2未知")
    private Integer gander;

    /**
     * 监护人
     */
    @ApiModelProperty(value = "监护人")
    private List<GuardianBO> guardians;

    /**
     * 是否高危体弱
     */
    @ApiModelProperty(value = "是否高危体弱")
    private Boolean isWeak;

    /**
     * 特殊情况
     */
    @ApiModelProperty(value = "特殊情况")
    private String specialCase;
}
