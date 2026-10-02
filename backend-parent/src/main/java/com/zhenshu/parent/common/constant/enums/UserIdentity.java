package com.zhenshu.parent.common.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.parent.common.library.knife4j.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author zch
 * @version 1.0
 * @desc 用户身份
 * @date 2022-06-24
 **/
@AllArgsConstructor
public enum UserIdentity implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 身份 0-表示管理员 1-普通人员
     */
    ADMIN(0, "管理员"),
    PEOPLE(1, "普通员工");

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
