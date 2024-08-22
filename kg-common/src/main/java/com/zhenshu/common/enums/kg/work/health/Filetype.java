package com.zhenshu.common.enums.kg.work.health;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/7 15:52
 * @desc 附件类型
 */
@Getter
@AllArgsConstructor
public enum Filetype implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 0病历 1处方 2清单
     */
    CASE_HISTORY(0, "病历"),
    PRESCRIPTION(1, "处方"),
    INVENTORY(2, "清单");

    private final int value;

    private final String info;


    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public Integer getCode() {
        return value;
    }
}
