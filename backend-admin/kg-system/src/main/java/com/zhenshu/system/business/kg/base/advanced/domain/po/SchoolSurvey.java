package com.zhenshu.system.business.kg.base.advanced.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.common.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-03-01
 * @desc 园区概况表 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kg_school_survey")
public class SchoolSurvey extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 园区概况ID
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
     * 图片url
     */
    private String imgUrl;

    /**
     * 内容
     */
    private String surveyContent;

}
