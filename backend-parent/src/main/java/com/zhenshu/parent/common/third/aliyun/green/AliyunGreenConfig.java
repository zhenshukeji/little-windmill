package com.zhenshu.parent.common.third.aliyun.green;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/4/25 13:46
 * @desc
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun-green")
public class AliyunGreenConfig {
    private String regionId;

    private String accessKeyId;

    private String accessKeySecret;
}
