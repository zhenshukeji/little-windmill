package com.zhenshu.parent.common.third.wx.zhenshu.domain;

import lombok.Data;

/**
 * @author jing
 * @version 1.0
 * @desc 校验openid返回
 * @date 2021/9/8 0008 20:21
 **/
@Data
public class CheckOpenidResult {

    private String ret;

    public Content data;

    @Data
    public class Content {

        private Integer errcode;

    }

}
