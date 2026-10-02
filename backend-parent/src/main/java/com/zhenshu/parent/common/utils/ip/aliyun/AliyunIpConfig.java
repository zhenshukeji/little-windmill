package com.zhenshu.parent.common.utils.ip.aliyun;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yuxi
 * @version 1.0
 * @date 2021/2/4 22:00
 * @desc 阿里云cdn配置
 */
@Component
@ConfigurationProperties(prefix = "aliyun-ip")
public class AliyunIpConfig {

    private String regionId;
    private String accessKeyId;
    private String accessKeySecret;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

}
