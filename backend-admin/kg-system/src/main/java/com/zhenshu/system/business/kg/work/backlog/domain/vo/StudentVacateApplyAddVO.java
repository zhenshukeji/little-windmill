package com.zhenshu.system.business.kg.work.backlog.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author xxx
 * @version 1.0
 * @date 2022-03-07
 * @desc 学生请假申请表 新增入参
 */
@Data
@ApiModel(description = "学生请假申请表 新增入参")
public class StudentVacateApplyAddVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "学生id")
    private Long studentId;

    /**
     * 请假类型 0病假 1事假
     */
    @NotNull
    @ApiModelProperty(required = true, value = "请假类型 0病假 1事假")
    private Integer vacateType;

    /**
     * 请假开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    @ApiModelProperty(required = true, value = "请假开始时间")
    private LocalDateTime beginTime;

    /**
     * 请假结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    @ApiModelProperty(required = true, value = "请假结束时间")
    private LocalDateTime endTime;

    /**
     * 请假原因
     */
    @Xss
    @Size(max = 200)
    @NotEmpty
    @ApiModelProperty(required = true, value = "请假原因")
    private String vacateReason;

}
