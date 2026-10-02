package com.zhenshu.common.enums.kg.work.educational;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * 接收人职业
 *
 * @Author xuzq
 * @Date 2022/9/30 16:54
 * @Version 1.0
 */

@AllArgsConstructor
public enum ReceiverJob implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 接收人职业 1保健医生 2教师 3家长 4游客
     */
    CARE_DOCTOR(1, "保健医生"),
    TEACHER(2, "教师"),
    PARENTS(3, "家长"),
    TOURISTS(4, "游客");

    /**
     * 值
     */
    private Integer code;

    /**
     * 描述
     */
    private String info;

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
