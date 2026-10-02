package com.zhenshu.parent.common.config.aspect.login;

import springfox.documentation.annotations.ApiIgnore;

import java.lang.annotation.*;


/**
 * 获取当前登录的学生id
 *
 * @author jing
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiIgnore
public @interface LoginInfo {
}
