package com.zhenshu.parent.common.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.parent.common.library.knife4j.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/7/1 11:52
 * @desc
 */
@Getter
@AllArgsConstructor
public enum LastReadTimeType implements IBaseEnum<String>, IEnum<String> {
    /**
     *
     */
    ILLNESS("ILLNESS", "疾病"),
    INVASION("INVASION", "发病"),
    ACCIDENT("ACCIDENT", "意外事故"),
    CONTAGION("CONTAGION", "传染病");

    /**
     * 实际值
     */
    private final String code;
    /**
     * 描述
     */
    private final String info;

    @Override
    public String getValue() {
        return code;
    }
}
