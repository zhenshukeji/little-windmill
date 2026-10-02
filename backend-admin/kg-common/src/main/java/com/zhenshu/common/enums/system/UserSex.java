package com.zhenshu.common.enums.system;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/11 11:41
 * @desc 用户性别枚举
 */
@Getter
@AllArgsConstructor
public enum UserSex implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 男
     */
    MAN(0, "男"),

    /**
     * 女
     */
    WOMAN(1, "女"),

    /**
     * 未知
     */
    UNKNOWN(2, "未知");

    /**
     * 实际值
     */
    private final Integer value;

    /**
     * 描述
     */
    private final String info;

    @Override
    public Integer getCode() {
        return value;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + "-" + info;
    }
}
