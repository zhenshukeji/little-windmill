package com.zhenshu.parent.common.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.parent.common.library.knife4j.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/3/7 10:33
 * @desc 申请状态
 */
@Getter
@AllArgsConstructor
public enum ApplyStatus implements IBaseEnum<Integer>, IEnum<Integer> {
    /**
     * 待处理
     */
    PENDING(0, "待处理"),

    /**
     * 已同意
     */
    CONSENT(1, "已同意"),

    /**
     * 拒绝
     */
    REFUSE(2, "拒绝"),

    /**
     * 撤回
     */
    RECALL(3, "撤回");

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
