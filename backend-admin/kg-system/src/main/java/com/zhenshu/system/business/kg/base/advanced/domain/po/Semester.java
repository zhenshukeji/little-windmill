package com.zhenshu.system.business.kg.base.advanced.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.common.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-03-01
 * @desc 学期表 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kg_semester")
public class Semester extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学期ID
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
     * 学期名称
     */
    private String semesterName;

    /**
     * 开始日期
     */
    private Date beginDate;

    /**
     * 结束日期
     */
    private Date endDate;

}
