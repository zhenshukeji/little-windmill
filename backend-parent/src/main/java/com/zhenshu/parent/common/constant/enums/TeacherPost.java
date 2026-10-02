package com.zhenshu.parent.common.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.parent.common.library.knife4j.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author zch
 * @version 1.0
 * @desc 教师岗位
 * @date 2022-06-24
 **/
@AllArgsConstructor
public enum TeacherPost implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 教师岗位 0班主任 1副班主任
     */
    TEACHER(0, "班主任 "),
    SUB_TEACHER(1, "副班主任");

    private final int code;
    private final String info;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public Integer getValue() {
        return code;
    }

    @Override
    public String toString() {
        return code + "-" + info;
    }
}
