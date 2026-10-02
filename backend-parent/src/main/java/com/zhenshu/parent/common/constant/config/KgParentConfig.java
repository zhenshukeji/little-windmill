package com.zhenshu.parent.common.constant.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/1 16:34
 * @desc
 */
@Component
@ConfigurationProperties(prefix = "kg-parent-config")
@Data
public class KgParentConfig {
    private OrderConfig orderConfig;

    @Data
    public static class OrderConfig {
        /**
         * 支付回调地址
         */
        private String payNotifyUrl;

        /**
         * 支付时候的描述
         */
        private String payDesc;
    }
}
