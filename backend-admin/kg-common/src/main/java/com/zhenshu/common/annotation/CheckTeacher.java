package com.zhenshu.common.annotation;

import com.zhenshu.common.constant.ErrorEnums;

import java.lang.annotation.*;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/8 14:51
 * @desc 检查教师身份, 校区管理员不参与检查
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckTeacher {
    /**
     * 检查的期望结果; 不是预期结果会抛出异常
     */
    boolean value();

    /**
     * 抛出的异常
     */
    ErrorEnums error();
}
