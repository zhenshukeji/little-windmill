package com.zhenshu.common.utils.poi;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/7/22 11:37
 * @desc Boolean类型转换器
 */
public class BooleanExcelHandlerAdapter implements ExcelHandlerAdapter {
    @Override
    public Object format(Object value, String[] args) {
        if (value instanceof Boolean && args.length >= 2) {
            Boolean bool = (Boolean) value;
            return bool ? args[0] : args[1];
        }
        return null;
    }
}
