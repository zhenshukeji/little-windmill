package com.zhenshu.parent.common.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.parent.common.library.knife4j.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author jing
 * @version 1.0
 * @desc 示例：可以直接转换成数据库对象和文档对象的枚举类型怎么写，需要继承IBaseEnum 和 IEnum ，其中IBaseEnum是文档对象，IEnum 是数据库对象需要，其余是重写的方法
 * @date 2022/2/21 0021 11:33
 **/
@AllArgsConstructor
public enum ExampleEnum implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 示例
     */
    ONE(1, "一"),
    TWO(2, "二"),
    ;

    private Integer code;
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
