package com.zhenshu.parent.app.domain.dto;

import lombok.Data;

/**
 * 微信小程序登录信息
 *
 * @author jing
 */
@Data
public class WxPhoneInfo {

    /**
     * errcode : 0
     * errmsg : ok
     * phone_info : {"phoneNumber":"18370061293","purePhoneNumber":"18370061293","countryCode":"86","watermark":{"timestamp":1650528877,"appid":"${WX_APP_ID:your-app-id}"}}
     */
    private int errcode;
    private String errmsg;
    private PhoneInfoBean phone_info;

    @Data
    public static class PhoneInfoBean {

        private String phoneNumber;
        private String purePhoneNumber;
        private String countryCode;
        private WatermarkBean watermark;

        @Data
        public static class WatermarkBean {

            private int timestamp;
            private String appid;

        }
    }
}
