package com.zhenshu.system.cache.dto;

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
public class MiniStudentBO {

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
     * 集团名称
     */
    @ApiModelProperty("集团名称")
    private String blocName;

    /**
     * 校区名称
     */
    @ApiModelProperty("校区名称")
    private String kgName;

    /**
     * 班级名称
     */
//    @ApiModelProperty("班级名称")
//    private String className;

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
