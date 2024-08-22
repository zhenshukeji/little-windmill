package com.zhenshu.common.web.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerProperties
 *
 * @author Johnny Lu
 * @date 2021-05-29
 */

@Data
@Configuration
@ConfigurationProperties("swagger")
public class SwaggerProperties {
    private Boolean enabled;

    private String title = "";
    private String description = "";
    private String version = "";
    private String license = "";
    private String licenseUrl = "";
    private String termsOfServiceUrl = "";
    private String host = "";

    private GroupInfo bloc;
    private GroupInfo kgHome;
    private GroupInfo kgWork;
    private GroupInfo kgHealth;
    private GroupInfo kgFinance;
    private GroupInfo kgBase;
    private GroupInfo platform;
    private GroupInfo ruoyi;

    @Data
    public static class GroupInfo {
        private String basePackage = "";
        private String groupName = "";
        private String pathMapping = "";
    }


}
