package com.zhenshu.common.annotation;

import java.lang.annotation.*;

/**
 * @author jing
 * @version 1.0
 * @desc 用于做防重点的锁
 * @date 2021/2/19 0019 10:59
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LockFunction {

    /**
     * 类名称，配合methodName属性拼接后作为缓存key
     */
    String className();

    /**
     * 方法名称，用于区分不同的请求
     */
    String methodName();

    /**
     * key名称，isObj为true时必填
     */
    String keyName() default "";

    /**
     * 是否使用请求头jwt解析后的值作为参数
     */
    boolean fromJwt() default false;

    /**
     * 入参是object 还是一个基本类型包装类，比如long
     */
    boolean isObj() default true;

}
