package com.zhenshu.system.business.kg.work.educational.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import com.zhenshu.common.enums.kg.work.educational.TeachingProgramRange;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询教学计划入参")
public class TeachingProgramQueryVO extends PageEntity {
    /**
     * 教学计划范围
     */
    @ApiModelProperty(value = "教学计划范围")
    private TeachingProgramRange range;
    /**
     * 班级id;
     */
    @ApiModelProperty(value = "班级id")
    private Long classId;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间",hidden = true)
    private LocalDate beginDate;

}
