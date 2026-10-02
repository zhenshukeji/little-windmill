package com.zhenshu.parent.common.third.wx.domain;

import lombok.Data;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/24 9:27
 * @desc 微信用户对象
 */
@Data
public class UserData {
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像链接
     */
    private String headimgurl;

    /**
     * openid
     */
    private String openid;

}
