package com.zhenshu.parent.app.domain.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 监护人表
 * </p>
 *
 * @author jing
 * @since 2022-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("kg_guardian")
public class Guardian implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 监护人id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 集团id
     */
    private Long blocId;

    /**
     * 学校id
     */
    private Long kgId;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 监护人姓名
     */
    private String name;

    /**
     * 监护人与学生的关系
     */
    private String relation;

    /**
     * 电话
     */
    private String phone;

    /**
     * 职业
     */
    private String job;

    /**
     * 证件类型
     */
    private String cardType;

    /**
     * 证件号码
     */
    private String cardNumber;

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
     * 逻辑删除 0false 1true
     */
    @TableLogic
    private Boolean delFlag;


}
