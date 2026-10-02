package com.zhenshu.system.business.kg.base.advanced.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 查询班级表 入参
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询班级表 入参")
public class ClassroomQueryVO extends PageEntity {
}
