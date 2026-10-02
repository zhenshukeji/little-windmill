package com.zhenshu.common.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhenshu.common.constant.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Select;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/11 9:37
 * @desc 分页基础类
 */
@Data
@ApiModel(description = "分页入参")
public class PageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页数
     */
    @NotNull(groups = {Select.class})
    @Range(min = Constants.ONE, groups = {Select.class})
    @ApiModelProperty(required = true, value = "页数; 导出不用传递")
    private Integer pageNum;

    /**
     * 条数
     */
    @NotNull(groups = {Select.class})
    @Range(min = Constants.ONE, groups = {Select.class})
    @ApiModelProperty(required = true, value = "条数; 导出不用传递")
    private Integer pageSize;

    /**
     * 校区ID, 前端不用传递; 为了写MyBatis查询语句的时候方便取值;
     */
    @ApiModelProperty(value = "校区ID, 不用传递", hidden = true)
    private Long kgId;

    /**
     * 集团Id 前端不用传递; 为了写MyBatis查询语句的时候方便取值;
     */
    @ApiModelProperty(value = "集团Id 前端不用传递", hidden = true)
    private Long blocId;

    /**
     * 返回MyBaitsPlus的分页对象
     *
     * @param <T> 泛型
     * @return 结果
     */
    @ApiModelProperty(hidden = true)
    public <T> IPage<T> page() {
        return new Page<>(pageNum, pageSize);
    }
}
