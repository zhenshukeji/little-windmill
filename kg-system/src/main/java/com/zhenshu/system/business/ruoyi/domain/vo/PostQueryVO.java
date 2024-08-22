package com.zhenshu.system.business.ruoyi.domain.vo;

import com.zhenshu.common.domain.PageEntity;
import com.zhenshu.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/16 13:39
 * @desc 岗位查询入参
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "岗位查询入参")
public class PostQueryVO extends PageEntity {
    /**
     * 岗位名称
     */
    @Xss
    @Size(max = 50)
    @ApiModelProperty(value = "岗位名称")
    private String postName;
}
