package com.zhenshu.parent.app.domain.vo.apply;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.parent.common.constant.enums.VacateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/24 16:02
 * @desc 添加请假申请入参
 */
@Data
@ApiModel(description = "添加请假申请入参")
public class VacateApplyAddVO {
    /**
     * 请假类型 0病假 1事假
     */
    @NotNull
    @ApiModelProperty(required = true, value = "请假类型 0病假 1事假")
    private VacateType vacateType;

    /**
     * 请假开始时间
     */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(required = true, value = "请假开始时间")
    private LocalDateTime beginTime;

    /**
     * 请假结束时间
     */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(required = true, value = "请假结束时间")
    private LocalDateTime endTime;

    /**
     * 请假原因
     */
    @Size(max = 100)
    @NotBlank
    @ApiModelProperty(required = true, value = "请假原因")
    private String vacateReason;
}
