package com.zhenshu.system.business.kg.work.educational.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author zch
 * @version 1.0
 * @date 2022-05-11
 * @desc 查询入参
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询入参")
public class ClassCircleQueryVO extends PageEntity {
}
