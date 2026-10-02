package com.zhenshu.parent.common.utils.ip.baidu.domain;

import lombok.Data;

/**
 * @author jing
 * @version 1.0
 * @desc 百度云ip定位返回结果
 * @date 2021/9/1 0001 12:04
 **/
@Data
public class BaiduYunIpResult {
    /**
     * code : 200
     * message : 处理成功
     * data : {"area":"华东","country":"中国","long_ip":"3748183666","city":"济南","ip":"223.104.190.114","isp":"移动","region_id":"370000","region":"山东","country_id":"CN","city_id":"370100"}
     * trade_no : a48b9719a3f642f2b7196e7a1fbd9ac4
     */

    private Integer code;
    private String message;
    private DataBean data;
    private String trade_no;

    @Data
    public static class DataBean {
        /**
         * area : 华东
         * country : 中国
         * long_ip : 3748183666
         * city : 济南
         * ip : 223.104.190.114
         * isp : 移动
         * region_id : 370000
         * region : 山东
         * country_id : CN
         * city_id : 370100
         */

        private String area;
        private String country;
        private String long_ip;
        private String city;
        private String ip;
        private String isp;
        private String region_id;
        private String region;
        private String country_id;
        private String city_id;

    }
}
