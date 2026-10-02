package com.zhenshu.parent.app.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 班级表 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kg_classroom")
public class Classroom extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级id
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
     * 班级名
     */
    private String className;

    /**
     * 年级id
     */
    private Long gradeId;

    /**
     * 班主任id; 绑定的是员工表的id
     */
    private Long teacherId;

    /**
     * 副班主任id; 绑定的是员工表的id
     */
    private Long subTeacherId;

    /**
     * 学生数量
     */
    private Integer studentCount;

}
