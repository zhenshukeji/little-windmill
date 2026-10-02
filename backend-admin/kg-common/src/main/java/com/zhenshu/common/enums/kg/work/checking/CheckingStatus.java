package com.zhenshu.common.enums.kg.work.checking;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/3/7 11:00
 * @desc 考勤状态
 */
@AllArgsConstructor
public enum  CheckingStatus  implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 病假
     */
    SICK_LEAVE(0, "病假"),

    /**
     * 事假
     */
    PERSONAL_LEAVE(1, "事假"),

    /**
     * 在园
     */
    IN_SCHOOL(2, "在园"),

    /**
     * 缺勤
     */
    ABSENCE(3, "缺勤");

    /**
     * 实际值
     */
    private final Integer code;
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

