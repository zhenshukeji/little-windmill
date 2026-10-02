package com.zhenshu.parent.app.domain.bo.program;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author zch
 * @version 1.0
 * @desc 教学计划
 * @date 2022-06-21
 **/
@Data
@ApiModel
public class TeachingProgramPartBO {
    /**
     * 教学计划id
     */
    private Long id;

    /**
     * 教学计划照片
     */
    private String planUrl;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 开始时间
     */
    private LocalDate beginDate;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
