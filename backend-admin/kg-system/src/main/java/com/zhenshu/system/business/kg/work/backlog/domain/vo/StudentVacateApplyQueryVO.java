package com.zhenshu.system.business.kg.work.backlog.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author xxx
 * @version 1.0
 * @date 2022-03-07
 * @desc 查询学生请假申请表 入参
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询学生请假申请表 入参")
public class StudentVacateApplyQueryVO extends PageEntity {
    /**
     * 学生姓名
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(required = false, value = "学生姓名")
    private String studentName;

    /**
     * 班级id
     */
    @ApiModelProperty(value = "班级id", hidden = true)
    private Long classroomId;

}
