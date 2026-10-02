package com.zhenshu.parent.common.third.certification;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jing
 * @version 1.0
 * @desc 实名认证
 * @date 2022/4/6 0006 14:24
 **/
@Component
@ConfigurationProperties(prefix = "cert")
@Data
public class CertificationConfig {

    private String appCode;
}
