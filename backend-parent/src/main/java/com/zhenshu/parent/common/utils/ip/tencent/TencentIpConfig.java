package com.zhenshu.parent.common.utils.ip.tencent;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jing
 * @version 1.0
 * @desc 腾讯云ip地址查找config
 * @date 2021/8/19 0019 16:16
 **/
@Component
@Data
@ConfigurationProperties(prefix = "tencent-ip")
public class TencentIpConfig {

    /**
     * 请求token
     */
    private String key;
}
