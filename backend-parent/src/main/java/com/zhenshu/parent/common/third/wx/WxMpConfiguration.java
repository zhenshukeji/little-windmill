package com.zhenshu.parent.common.third.wx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Constructor;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/24 9:35
 * @desc 微信授权配置类
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfiguration {

    private final WxMpProperties wxMpProperties;

    public WxMpConfiguration(WxMpProperties wxMpProperties) {
        this.wxMpProperties = wxMpProperties;
    }

    @Bean(value = "wxMpService")
    @ConditionalOnMissingBean(value = {WxMpService.class})
    public WxMpService wxMpService() {
        WxMpService wxMpService;
        try {
            Constructor<? extends WxMpService> constructor = wxMpProperties.getService().getServiceClass().getConstructor();
            wxMpService = constructor.newInstance();
            wxMpService.setWxMpProperties(wxMpProperties);
            return wxMpService;
        } catch (Exception e) {
            log.error("Bean: wxMpService装配失败; 原因: {}", e.getMessage());
        }
        return null;
    }
}
