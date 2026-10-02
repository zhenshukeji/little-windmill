package com.zhenshu.parent.app.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zch
 * @version 1.0
 * @desc 总观看或下载次数
 * @date 2022-06-21
 **/
@Data
@ApiModel
public class WatchCountBO {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 累计观看次数
     */
    @ApiModelProperty(value = "累计观看次数")
    private Integer watchCount;
}
