package com.zhenshu.parent.common.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.parent.common.library.knife4j.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-28
 * @desc 上传文件类型
 */

@AllArgsConstructor
public enum CircleUploadType implements IBaseEnum<Integer>, IEnum<Integer> {

    /**
     * 图片
     */
    UPLOAD_PICTURE(0, "图片"),
    /**
     * 视频
     */
    UPLOAD_VIDEO(1, "视频");
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
}
