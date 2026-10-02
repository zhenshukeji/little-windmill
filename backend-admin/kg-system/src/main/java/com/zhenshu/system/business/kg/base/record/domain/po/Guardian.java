package com.zhenshu.system.business.kg.base.record.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.common.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-23
 * @desc 监护人表 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kg_guardian")
public class Guardian extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 监护人id
     */
    @TableId(type = IdType.AUTO)
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

}
