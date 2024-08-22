package com.zhenshu.common.enums.kg.work.checking;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-26 11:00
 * @desc 是否刷脸
 */
@AllArgsConstructor
public enum HasFaceSwiping implements IEnum<Boolean> {
    /**
     * 未刷脸
     */
    NO_FACE_SWIPING(false, "否"),

    /**
     * 已刷脸
     */
    YES_FACE_SWIPING(true, "是");

    /**
     * 实际值
     */
    private Boolean code;
    /**
     * 描述
     */
    private String info;

    public Boolean getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public Boolean getValue() {
        return code;
    }

    @Override
    public String toString() {
        return code + "-" + info;
    }
}

