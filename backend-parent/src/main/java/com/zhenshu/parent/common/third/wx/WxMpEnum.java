package com.zhenshu.parent.common.third.wx;

import com.zhenshu.parent.common.third.wx.tuzhan.TuZhanWxMpService;
import com.zhenshu.parent.common.third.wx.zhenshu.ZhenShuWxMpService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/24 10:45
 * @desc 微信授权枚举
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum WxMpEnum {
    /**
     * 珍数
     */
    ZHENSHU("http://120.77.218.29:5900/wxservice/code_user", ZhenShuWxMpService.class),
    /**
     * 兔展
     */
    TUZHAN("https://pro.tuyoumi.com/%s-open-service/web/user_info", TuZhanWxMpService.class);

    public String codeUrl;

    public Class<? extends WxMpService> serviceClass;
}
