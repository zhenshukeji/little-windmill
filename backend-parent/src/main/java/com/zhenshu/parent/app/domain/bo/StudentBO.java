package com.zhenshu.parent.app.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author jing
 * @version 1.0
 * @desc 学生信息
 * @date 2022/5/24 0024 15:51
 **/
@Data
@ApiModel
public class StudentBO {

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
     * 生日日期
     */
    @ApiModelProperty("生日日期")
    private LocalDate birthdate;

    /**
     * 校区id
     */
    @ApiModelProperty("校区id")
    private Long kgId;

    /**
     * 集团id
     */
    @ApiModelProperty("集团id")
    private Long blocId;

    /**
     * 班级id
     */
    @ApiModelProperty("班级id")
    private Long classId;
}
