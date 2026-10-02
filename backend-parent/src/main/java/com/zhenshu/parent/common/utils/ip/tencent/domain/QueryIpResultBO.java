package com.zhenshu.parent.common.utils.ip.tencent.domain;

import lombok.Data;

/**
 * @author jing
 * @version 1.0
 * @desc 定位行政区划信息
 * @date 2021/8/19 0019 16:26
 **/
@Data
public class QueryIpResultBO {

    /**
     * 状态码，0 为正常，其它为异常，详细请参阅
     */
    private Integer status;

    /**
     * 对 status 的描述
     */
    private String message;

    /**
     * IP 定位结果
     */
    private ResultBean result;

    @Data
    public static class ResultBean {

        /**
         * 用于定位的 IP 地址
         */
        private String ip;

        /**
         * 定位坐标
         */
        private LocationBean location;

        /**
         * 定位行政区划信息
         */
        private AdInfoBean ad_info;

        @Data
        public static class LocationBean {

            /**
             * 纬度
             */
            private double lat;
            /**
             * 经度
             */
            private double lng;
        }

    }
}
