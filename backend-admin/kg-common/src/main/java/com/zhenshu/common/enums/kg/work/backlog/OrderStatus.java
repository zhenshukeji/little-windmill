package com.zhenshu.common.enums.kg.work.backlog;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @Author xuzq
 * @Date 2022/9/27 16:38
 * @Version 1.0
 */
@AllArgsConstructor
public enum OrderStatus implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 未预约
     */
    NO_RESERVATIONS(0, "未预约"),

    /**
     * 预约未满
     */
    RESERVATIONS_ENOUGH(1, "预约未满"),

    /**
     * 预约已满
     */
    RESERVATIONS_FULL(2, "预约已满");

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
