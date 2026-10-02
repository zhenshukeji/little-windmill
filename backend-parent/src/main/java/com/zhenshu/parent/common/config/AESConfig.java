package com.zhenshu.parent.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jing
 * @version 1.0
 * @desc aes 配置
 * @date 2021/2/7 0007 11:41
 **/
@Component
@Data
@ConfigurationProperties(prefix = "aes")
public class AESConfig {

    private String key;

}
