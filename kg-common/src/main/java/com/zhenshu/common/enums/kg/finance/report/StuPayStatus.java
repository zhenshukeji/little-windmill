package com.zhenshu.common.enums.kg.finance.report;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author jing
 * @version 1.0
 * @desc 学生类型
 * @date 2022/2/21 0021 11:33
 **/
@AllArgsConstructor
public enum StuPayStatus implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 学生类型 0缴清学生 1欠费学生
     */
    ALL_PAY(0, "缴清学生"),
    ARREARS(1, "欠费学生"),
    ;

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
        return code + "-" + info;
    }
}
