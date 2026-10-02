package com.zhenshu.common.web.swagger;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author jing
 * @version 1.0
 * @desc 给文档使用的
 * @date 2022/2/16 0016 10:54
 **/
public interface IBaseEnum<M> {

    /**
     * 枚举对应的值
     * @return 值
     */
    @JsonValue
    M getCode();

    /**
     * 枚举对应的注释
     * @return 注释
     */
    String getInfo();

}
