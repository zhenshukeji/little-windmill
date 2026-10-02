package com.zhenshu.common.enums.kg.work.backlog;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/3/7 9:58
 * @desc 请假类型
 */
@AllArgsConstructor
public enum VacateType implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 病假
     */
    SICK_LEAVE(0, "病假"),

    /**
     * 事假
     */
    PERSONAL_LEAVE(1, "事假");

    /**
     * 实际值
     */
    private final int code;
    /**
     * 描述
     */
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
