package com.zhenshu.common.enums.kg.base.record;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/22 10:02
 * @desc 校园员工查询类型
 */
@AllArgsConstructor
public enum KgStudentQueryType implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 在校学生
     */
    ON_SCHOOL(0, "在校学生"),

    /**
     * 历史档案
     */
    HISTORY(1, "历史档案");

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
