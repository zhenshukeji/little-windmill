package com.zhenshu.common.enums.kg.work.health;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-27 11:00
 * @desc 出勤状况
 */
@AllArgsConstructor
public enum AttendanceStatus implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 未出勤
     */
    ABSENCE(0, "未出勤"),

    /**
     * 已出勤
     */
    ATTENDANCE(1, "已出勤"),
    ;

    /**
     * 实际值
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

