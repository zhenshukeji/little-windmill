package com.zhenshu.system.business.kg.home.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/9 19:44
 * @desc 今日待办出参
 */
@Data
@ApiModel(description = "今日待办出参")
public class KgHomePendingBO {
    /**
     * 待办数量
     */
    @ApiModelProperty(value = "待办数量")
    private Integer pendingCount;

    /**
     * 待办数据集合
     */
    @ApiModelProperty(value = "待办数据集合")
    private List<KgHomePendingItemBO> pendingList;
}
