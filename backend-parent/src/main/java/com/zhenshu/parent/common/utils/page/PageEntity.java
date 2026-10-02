package com.zhenshu.parent.common.utils.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
    @NotNull(groups = {IPage.class})
    @Range(min = 1, groups = {IPage.class})
    @ApiModelProperty(required = true, value = "页数")
    private Integer pageNum;

    /**
     * 条数
     */
    @NotNull(groups = {IPage.class})
    @Range(min = 1, groups = {IPage.class})
    @ApiModelProperty(required = true, value = "条数")
    private Integer pageSize;

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
