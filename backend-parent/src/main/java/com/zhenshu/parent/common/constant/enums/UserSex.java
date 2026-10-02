package com.zhenshu.parent.common.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.parent.common.library.knife4j.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author zch
 * @version 1.0
 * @desc 性别
 * @date 2022-06-24
 **/
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

    private final int code;
    private final String info;

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
