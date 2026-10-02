package com.zhenshu.parent.common.config.aspect.log;

import java.lang.annotation.*;

/**
 * @author jing
 * @version 1.0
 * @desc 日志打印
 * @date 2021/2/19 0019 10:59
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LogFunction {



}
