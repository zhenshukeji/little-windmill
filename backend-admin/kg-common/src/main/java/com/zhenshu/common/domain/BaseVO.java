package com.zhenshu.common.domain;

import com.zhenshu.common.utils.DateUtils;
import com.zhenshu.common.utils.StringUtils;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:55
 * @desc 通用属性
 */
public class BaseVO implements Serializable {

    protected LocalDate strToLocalDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        } else {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(DateUtils.YYYY_MM_DD));
        }
    }
}
