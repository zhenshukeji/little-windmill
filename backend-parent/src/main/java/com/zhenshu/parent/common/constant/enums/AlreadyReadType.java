package com.zhenshu.parent.common.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.parent.common.library.knife4j.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/29 16:18
 * @desc 已读类型
 */
@Getter
@AllArgsConstructor
public enum AlreadyReadType implements IBaseEnum<String>, IEnum<String> {
    /**
     *
     */
    VACATE("VACATE", "学生请假"),
    HEY_MEDICINE("HEY_MEDICINE", "喂药");

    /**
     * 实际值
     */
    private final String code;
    /**
     * 描述
     */
    private final String info;

    @Override
    public String getValue() {
        return code;
    }
}
