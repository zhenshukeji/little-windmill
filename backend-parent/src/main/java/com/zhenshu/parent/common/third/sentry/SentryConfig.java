package com.zhenshu.parent.common.third.sentry;

import io.sentry.Sentry;
import io.sentry.SentryClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * @author yuxi
 * @version 1.0
 * @date 2021/3/10 20:51
 * @desc sentry配置
 */

@Component
@ConfigurationProperties(prefix = "sentry")
public class SentryConfig {

    private boolean enable;

    private String dsn;

    public String getDsn() {
        return dsn;
    }

    public void setDsn(String dsn) {
        this.dsn = dsn;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Bean
    public SentryClient sentryClient() {
        return Sentry.init(dsn);
    }

}
