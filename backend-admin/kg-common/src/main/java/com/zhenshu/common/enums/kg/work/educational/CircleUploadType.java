package com.zhenshu.common.enums.kg.work.educational;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.web.swagger.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-09
 * @desc 上传图片类型
 */
@AllArgsConstructor
public enum CircleUploadType implements IBaseEnum<Integer>, IEnum<Integer> {
    UPLOAD_PICTURE(0, "图片"),
    UPLOAD_VIDEO(1, "视频");
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
    @Override
    public String toString() {
        return code + "-" + info;
    }
}
