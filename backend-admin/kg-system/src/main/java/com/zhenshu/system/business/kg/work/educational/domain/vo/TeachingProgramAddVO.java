package com.zhenshu.system.business.kg.work.educational.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-09
 * @desc 教学计划表 新增入参
 */
@Data
@ApiModel(description = "教学计划 新增入参")
public class TeachingProgramAddVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @ApiModelProperty(required = true, value = "开始时间")
    private LocalDate beginDate;

    /**
     * 教学计划图片
     */
    @Size(max = 200)
    @ApiModelProperty(required = true, value = "教学计划图片")
    private String planUrl;

    /**
     * 班级id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "班级id")
    private Long classId;
}
