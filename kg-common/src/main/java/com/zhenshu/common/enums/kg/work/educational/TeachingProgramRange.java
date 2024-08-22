package com.zhenshu.common.enums.kg.work.educational;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-09
 * @desc 教学计划筛选条件
 */
@AllArgsConstructor
public enum TeachingProgramRange implements IBaseEnum<Integer>, IEnum<Integer> {
    WEEK_TEACH_PLANS(0, "本周教学计划"),
    ALL_TEACH_PLANS(1, "全部教学计划");
    private Integer code;

    private String info;

    @Override
    public Integer getValue() {
        return code;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getInfo() {
        return info;
    }
}
