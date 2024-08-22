package com.zhenshu.common.enums.platform;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/11 10:55
 * @desc 集团状态枚举
 */
@Getter
@AllArgsConstructor
public enum BlocStatus {
    /**
     * 有效
     */
    VALID(0, "有效"),

    /**
     * 过期
     */
    EXPIRES(1, "过期");

    /**
     * 实际值
     */
    private final Integer value;

    /**
     * 描述
     */
    private final String info;
}
