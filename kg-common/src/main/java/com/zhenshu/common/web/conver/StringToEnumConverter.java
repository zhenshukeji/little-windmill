package com.zhenshu.common.web.conver;

import cn.hutool.core.util.ObjectUtil;
import com.zhenshu.common.web.swagger.IBaseEnum;
import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jing
 * @version 1.0
 * @desc 枚举编码 -> 枚举 转化器
 * @date 2022/2/21 0021 19:49
 **/
public class StringToEnumConverter<T extends IBaseEnum> implements Converter<String, T> {
    private Map<String, T> enumMap = new HashMap<>();

    public StringToEnumConverter(Class<T> enumType) {
        T[] enums = enumType.getEnumConstants();
        for (T e : enums) {
            enumMap.put(e.getCode() + "", e);
        }
    }

    @Override
    public T convert(String source) {
        T t = enumMap.get(source);
        if (ObjectUtil.isNull(t)) {
            throw new IllegalArgumentException("无法匹配对应的枚举类型");
        }
        return t;
    }
}
