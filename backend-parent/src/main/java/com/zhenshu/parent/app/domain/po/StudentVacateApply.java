package com.zhenshu.parent.app.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.parent.common.constant.enums.VacateType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-03-07
 * @desc 学生请假申请表 实体
 */
@Data
@TableName("kg_student_vacate_apply")
public class StudentVacateApply extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请假id
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
     * 班级id
     */
    private Long classId;

    /**
     * 申请人
     */
    private Long applyBy;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 请假类型 0病假 1事假
     */
    private VacateType type;

    /**
     * 请假开始时间
     */
    private LocalDateTime beginTime;

    /**
     * 请假结束时间
     */
    private LocalDateTime endTime;

    /**
     * 请假原因
     */
    private String reason;

    /**
     * 审批人
     */
    private Long approveBy;

    /**
     * 审批时间
     */
    private LocalDateTime approveTime;

    /**
     * 审批意见
     */
    private String approveOpinion;

    /**
     * 申请状态
     */
    private Integer status;

}
