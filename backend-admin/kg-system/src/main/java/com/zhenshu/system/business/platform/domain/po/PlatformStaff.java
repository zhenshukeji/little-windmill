package com.zhenshu.system.business.platform.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 平台人员表
 * </p>
 *
 * @author Jing
 * @since 2022-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("kg_platform_staff")
public class PlatformStaff implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 人员名称
     */
    private String staffName;

    /**
     * 登录账号
     */
    private String staffPhone;

    /**
     * 是否删除
     */
    private Boolean delFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
