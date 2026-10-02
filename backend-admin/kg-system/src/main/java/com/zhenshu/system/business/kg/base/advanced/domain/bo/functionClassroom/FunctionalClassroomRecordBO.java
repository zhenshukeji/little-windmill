package com.zhenshu.system.business.kg.base.advanced.domain.bo.functionClassroom;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author xuzq
 * @Date 2022/9/28 19:24
 * @Version 1.0
 */
@Data
@ApiModel(description = "功能课室出参")
public class FunctionalClassroomRecordBO {

    /**
     * 课室总数
     */
    @ApiModelProperty(required = true, value = "课室总数")
    private Integer count;

    /**
     * 功能课室列表
     */
    @ApiModelProperty(required = true, value = "功能课室列表")
    private List<FunctionalClassroomBO> functionalClassroomBOList;
}
