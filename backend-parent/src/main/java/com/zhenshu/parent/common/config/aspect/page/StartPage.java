package com.zhenshu.parent.common.config.aspect.page;

import java.lang.annotation.*;

/**
 * @author jing
 * @version 1.0
 * @desc mybatis 分页
 * @date 2021/8/3 0003 15:49
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface StartPage {

}
