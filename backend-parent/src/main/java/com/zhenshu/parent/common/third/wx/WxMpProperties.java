package com.zhenshu.parent.common.third.wx;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jing
 * @version 1.0
 * @desc 微信公众号配置
 * @date 2021/9/8 0008 19:31
 **/
@Data
@ConfigurationProperties(prefix = "wx-mp")
public class WxMpProperties {
    /**
     * appId
     */
    private String appId;

    /**
     * 拓展属性
     */
    private String expand;

    /**
     * 使用的服务
     */
    private WxMpEnum service;

}
