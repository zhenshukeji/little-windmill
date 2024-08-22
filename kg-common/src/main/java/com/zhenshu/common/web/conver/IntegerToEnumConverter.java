package com.zhenshu.common.web.conver;

import cn.hutool.core.util.ObjectUtil;
import com.zhenshu.common.web.swagger.IBaseEnum;
import org.springframework.core.convert.converter.Converter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jing
 * @version 1.0
 * @desc 枚举转换
 * @date 2022/2/21 0021 19:39
 **/

public class IntegerToEnumConverter<T extends IBaseEnum> implements Converter<Integer, T> {
    private Map<Object, T> enumMap = new HashMap<>();

    public IntegerToEnumConverter(Class<T> enumType) {
        T[] enums = enumType.getEnumConstants();
        for (T e : enums) {
            enumMap.put(e.getCode(), e);
        }
    }

    @Override
    public T convert(Integer source) {
        T t = enumMap.get(source);
        if (ObjectUtil.isNull(t)) {
            throw new IllegalArgumentException("无法匹配对应的枚举类型");
        }
        return t;
    }
}
