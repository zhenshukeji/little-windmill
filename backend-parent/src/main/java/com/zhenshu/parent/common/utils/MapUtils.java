package com.zhenshu.parent.common.utils;

import com.zhenshu.parent.common.constant.enums.ErrorEnums;
import com.zhenshu.parent.common.constant.exception.ServiceException;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jing
 * @version 1.0
 * @desc map 工具类
 * @date 2021/7/29 0029 18:02
 **/
public class MapUtils {

    /**
     * map 转 对象
     *
     * @param map map
     * @param t   对象
     * @return 对象
     */

    public static <T> T mapToObj(Map<Object, Object> map, T t) {
        Map<String, Object> hashMap = new HashMap<>();
        map.forEach((key, value) -> {
            hashMap.put(key.toString(), value);
        });
        try {
            BeanUtils.populate(t, hashMap);
            return t;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ServiceException(ErrorEnums.INNER_ERROR);
        }
    }


    /**
     * 对象转 map
     *
     * @param t 对象
     * @return map
     */
    public static <T> Map<String, String> objToMap(T t) {
        try {
            return BeanUtils.describe(t);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ServiceException(ErrorEnums.INNER_ERROR);
        }
    }

}
