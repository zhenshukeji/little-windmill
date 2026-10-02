package com.zhenshu.system.business.kg.work.educational.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.common.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-09
 * @desc 教学计划 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kg_teaching_program")
public class TeachingProgram extends BasePO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 教学计划id
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
     * 班级id
     */
    private Long classId;
    /**
     * 教学计划照片 多个url以逗号分割
     */
    private String planUrl;
    /**
     * 开始时间
     */
    private LocalDate beginDate;

}
