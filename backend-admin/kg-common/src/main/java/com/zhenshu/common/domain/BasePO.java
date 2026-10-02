package com.zhenshu.common.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.utils.SecurityUtils;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:55
 * @desc 通用属性
 */
@Data
public class BasePO implements Serializable {
    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除(0:未删除 1:已删除)
     */
    @TableLogic
    private Boolean delFlag;

    /**
     * 初始化修改相关的属性; updateBy updateTime
     */
    public void initUpdateProp() {
        this.setUpdateBy(SecurityUtils.getUserId());
        this.setUpdateTime(LocalDateTime.now());
    }

    /**
     * 初始化新增相关的属性; createBy createTime delFlag
     */
    public void initCreateProp() {
        Long userId = SecurityUtils.getUserId();
        this.setCreateBy(userId == null ? Constants.ZERO : SecurityUtils.getUserId());
        this.setCreateTime(LocalDateTime.now());
        this.setDelFlag(Constants.FALSE);
    }
}
