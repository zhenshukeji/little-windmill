package com.zhenshu.parent.common.third.wx.tuzhan.domain;

import lombok.Data;

/**
 * @author jing
 * @version 1.0
 * @desc code 换取结果
 * @date 2022/1/20 0020 18:17
 **/
@Data
public class CodeChangeUser {

    /**
     * ok : false
     * error_code : -1101
     * message : 网页接口错误 : invalid code, rid: 61e936b9-276749dc-04f60413
     * msg : invalid code, rid: 61e936b9-276749dc-04f60413
     * code : 9999
     */
    private boolean ok;
    private int error_code;
    private String message;
    private String msg;
    private int code;
    /**
     * data : {"openid":"oiONR6Eax7h9ROOp3524hRnIOPAg","nickname":"景三","sex":0,"language":"","city":"","province":"","country":"","headimgurl":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKptR4JAQG44TMnMgoQQvws1UqoHZwuBSd7MRib1m2m3JdPZvkG2drEunFtuksk8FWTwQVIW2b7Y5Q/132","privilege":[],"unionid":"o8QwS59Amv2BGSNqsuAU79QMs5gg"}
     */
    private DataBean data;

    @Data
    public static class DataBean {

        /**
         * openid : oiONR6Eax7h9ROOp3524hRnIOPAg
         * nickname : 景三
         * sex : 0
         * language :
         * city :
         * province :
         * country :
         * headimgurl : https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKptR4JAQG44TMnMgoQQvws1UqoHZwuBSd7MRib1m2m3JdPZvkG2drEunFtuksk8FWTwQVIW2b7Y5Q/132
         * privilege : []
         * unionid : o8QwS59Amv2BGSNqsuAU79QMs5gg
         */
        private String openid;
        private String nickname;
        private int sex;
        private String language;
        private String city;
        private String province;
        private String country;
        private String headimgurl;
        private String unionid;
    }
}
