package com.zhenshu.parent.common.third.wx.zhenshu.domain;

import lombok.Data;

/**
 * @author jing
 * @version 1.0
 * @desc 校验openid返回
 * @date 2021/9/8 0008 20:21
 **/
@Data
public class CodeChangeResult {

    private String ret;

    public Content data;

    private String error;

    @Data
    public static class Content {

        private String nickname;

        private String headimgurl;

        private String openid;

    }
}
