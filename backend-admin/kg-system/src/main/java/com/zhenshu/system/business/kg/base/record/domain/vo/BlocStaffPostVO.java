package com.zhenshu.system.business.kg.base.record.domain.vo;

import com.zhenshu.common.constant.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/22 10:37
 * @desc 进入校区的集团人员分配岗位入参
 */
@Data
@ApiModel(description = "进入校区的集团人员分配岗位入参")
public class BlocStaffPostVO {
    /**
     * id; 对应kg_bloc_staff_kindergarten表的id
     */
    @NotNull
    @ApiModelProperty(required = true, value = "id")
    private Long id;

    /**
     * 岗位id集合
     */
    @NotNull
    @Size(min = Constants.ONE)
    @ApiModelProperty(required = true, value = "岗位id集合")
    private List<Long> postIds;
}
