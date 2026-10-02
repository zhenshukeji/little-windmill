package com.zhenshu.system.business.kg.work.backlog.domain.vo;

import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * @author xxx
 * @version 1.0
 * @date 2022-03-07
 * @desc 学生请假申请表 修改入参
 */
@Data
@ApiModel(description = "学生请假申请表 修改入参")
public class StudentVacateApplyEditVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请假id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "请假id")
    private Long id;

    /**
     * 处理结果
     */
    @NotNull
    @ApiModelProperty(required = true, value = "处理结果")
    private Boolean result;

    /**
     * 审批意见
     */
    @Xss
    @Size(max = 200)
    @ApiModelProperty(required = false, value = "审批意见")
    private String approveOpinion;

}
