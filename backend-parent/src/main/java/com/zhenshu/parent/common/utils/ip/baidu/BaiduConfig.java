package com.zhenshu.parent.common.utils.ip.baidu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jing
 * @version 1.0
 * @desc 百度云api
 * @date 2021/9/1 0001 11:35
 **/
@Component
@Data
@ConfigurationProperties(prefix = "baiduyun-ip")
public class BaiduConfig {

    /**
     * appCode
     */
    private String appCode;
}
