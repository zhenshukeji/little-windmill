package com.zhenshu.parent.common.third.wx;

import com.zhenshu.parent.common.third.wx.domain.UserData;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/24 9:26
 * @desc 微信授权
 */
public interface WxMpService {
    /**
     * 传递配置类
     *
     * @param wxMpProperties 配置类
     */
    void setWxMpProperties(WxMpProperties wxMpProperties);

    /**
     * code换取用户信息
     *
     * @param code code
     * @return 用户信息
     */
    UserData getUserInfo(String code);
}
