package com.zhenshu.system.cache.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhenshu.system.business.kg.base.record.domain.po.MiniLogin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jing
 * @version 1.0
 * @desc 小程序登录用户
 * @date 2022/7/4 0004 17:22
 **/
@Data
public class MiniLoginUser {

    /**
     * 当前选择的学生id
     */
    private Long studentId;

    /**
     * 当前选择的学生信息
     */
    private MiniStudentBO selectStudent;

    /**
     * 登录凭证
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String certificate;

    /**
     * 小程序用户信息
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private MiniLogin miniLogin;

    /**
     * 可选择的学生信息列表
     */
    private List<MiniStudentBO> studentList;

    /**
     * 获取小程序登录用户的id
     *
     * @return 结果
     */
    public Long getUserId() {
        return this.getMiniLogin().getId();
    }

    /**
     * 获取当前学生的班级id
     *
     * @return 结果
     */
    public Long getClassId() {
        return this.getSelectStudent().getClassId();
    }

    /**
     * 获取当前学生的校区id
     *
     * @return 结果
     */
    public Long getKgId() {
        return this.getSelectStudent().getKgId();
    }

    /**
     * 获取当前学生的集团id
     *
     * @return 结果
     */
    public Long getBlocId() {
        return this.getSelectStudent().getBlocId();
    }

}
