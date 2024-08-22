package com.zhenshu.common.enums.kg.health.health;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/29 10:27
 * @desc 疾病类型
 */
@Getter
@AllArgsConstructor
public enum IllnessType implements IBaseEnum<String>, IEnum<String> {
    /**
     * 疾病类型 slow慢性 fast急性
     */
    SLOW("SLOW", "慢性"),
    FAST("FAST", "急性");


    private final String value;

    private final String info;

    @Override
    public String getCode() {
        return value;
    }

    @Override
    public String getInfo() {
        return info;
    }
}
