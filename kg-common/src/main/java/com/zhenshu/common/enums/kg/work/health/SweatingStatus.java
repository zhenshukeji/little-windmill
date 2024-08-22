package com.zhenshu.common.enums.kg.work.health;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-06
 * @desc 出汗状态
 */
@AllArgsConstructor
public enum SweatingStatus implements IBaseEnum<Integer>, IEnum<Integer> {

    NO_HAS_SWEATING(0, "无"),
    HAS_SWEATING(1, "有");

    private Integer code;
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
        return "SweatingStatus{" +
                "code=" + code +
                ", info='" + info + '\'' +
                '}';
    }
}
