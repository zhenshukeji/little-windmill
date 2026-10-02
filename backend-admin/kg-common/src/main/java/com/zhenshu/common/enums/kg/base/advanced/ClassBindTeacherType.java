package com.zhenshu.common.enums.kg.base.advanced;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/22 10:02
 * @desc 班级绑定老师枚举
 */
@AllArgsConstructor
public enum ClassBindTeacherType implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 绑定班主任
     */
    PRIMARY(0, "班主任"),

    /**
     * 绑定副班主任
     */
    SUB(1, "副班主任");

    /**
     * 实际值
     */
    private Integer code;
    /**
     * 描述
     */
    private String info;

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
