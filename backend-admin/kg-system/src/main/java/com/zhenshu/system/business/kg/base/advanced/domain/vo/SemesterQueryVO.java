package com.zhenshu.system.business.kg.base.advanced.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-03-01
 * @desc 查询学期表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询学期表")
public class SemesterQueryVO extends PageEntity {
}
