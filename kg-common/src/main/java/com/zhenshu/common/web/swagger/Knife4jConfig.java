package com.zhenshu.common.web.swagger;


import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.zhenshu.common.constant.ErrorEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Knife4jConfig
 *
 * @author Johnny Lu
 * @date 2021-05-28
 */
@Configuration
@EnableSwagger2WebMvc
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
public class Knife4jConfig {

    private static final String AUTHORIZATION = "Authorization";

    @Resource
    private OpenApiExtensionResolver openApiExtensionResolver;

    @Bean
    @ConditionalOnMissingBean
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    @Bean(value = "ruoyi")
    public Docket h5Api(SwaggerProperties swaggerProperties) {
        // 配置全局状态码
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        Arrays.stream(ErrorEnums.values()).forEach(errorEnums -> responseMessageList.add(
                new ResponseMessageBuilder().code(errorEnums.getCode()).message(errorEnums.getMsg()).responseModel(
                        new ModelRef(errorEnums.getMsg())).build()
        ));
        SwaggerProperties.GroupInfo groupInfo = swaggerProperties.getRuoyi();
        return new Docket(DocumentationType.SWAGGER_2)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo(swaggerProperties))
                .groupName(groupInfo.getGroupName())
                .select()
                .apis(RequestHandlerSelectors.basePackage(groupInfo.getBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Collections.singletonList(securityContext()))
                .pathMapping(groupInfo.getPathMapping())
                // 激活插件，knife4j.setting 配置才能生效
                .extensions(openApiExtensionResolver.buildExtensions(groupInfo.getGroupName()));
    }

    @Bean(value = "bloc")
    public Docket bkApi(SwaggerProperties swaggerProperties) {
        SwaggerProperties.GroupInfo groupInfo = swaggerProperties.getBloc();
        return createDocker(groupInfo, swaggerProperties);
    }

    @Bean(value = "platform")
    public Docket platformApi(SwaggerProperties swaggerProperties) {
        SwaggerProperties.GroupInfo groupInfo = swaggerProperties.getPlatform();
        return createDocker(groupInfo, swaggerProperties);
    }

    @Bean(value = "kg-base")
    public Docket kgBaseApi(SwaggerProperties swaggerProperties) {
        SwaggerProperties.GroupInfo groupInfo = swaggerProperties.getKgBase();
        return createDocker(groupInfo, swaggerProperties);
    }

    @Bean(value = "kg-health")
    public Docket kgHealthApi(SwaggerProperties swaggerProperties) {
        SwaggerProperties.GroupInfo groupInfo = swaggerProperties.getKgHealth();
        return createDocker(groupInfo, swaggerProperties);
    }

    @Bean(value = "kg-finance")
    public Docket kgFinanceApi(SwaggerProperties swaggerProperties) {
        SwaggerProperties.GroupInfo groupInfo = swaggerProperties.getKgFinance();
        return createDocker(groupInfo, swaggerProperties);
    }

    @Bean(value = "kg-work")
    public Docket kgWorkApi(SwaggerProperties swaggerProperties) {
        SwaggerProperties.GroupInfo groupInfo = swaggerProperties.getKgWork();
        return createDocker(groupInfo, swaggerProperties);
    }

    @Bean(value = "kg-home")
    public Docket kgHomeApi(SwaggerProperties swaggerProperties) {
        SwaggerProperties.GroupInfo groupInfo = swaggerProperties.getKgHome();
        return createDocker(groupInfo, swaggerProperties);
    }

    private Docket createDocker(SwaggerProperties.GroupInfo groupInfo, SwaggerProperties swaggerProperties) {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo(swaggerProperties))
                .groupName(groupInfo.getGroupName())
                .select()
                .apis(RequestHandlerSelectors.basePackage(groupInfo.getBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchema())
                .securityContexts(Collections.singletonList(securityContext()))
                .pathMapping(groupInfo.getPathMapping())
                // 激活插件，knife4j.setting 配置才能生效
                .extensions(openApiExtensionResolver.buildExtensions(groupInfo.getGroupName()));
    }

    /**
     * 配置默认的全局鉴权策略的开关，通过正则表达式进行匹配；默认匹配所有URL
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(defaultAuth()))
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build();
    }

    private List<SecurityScheme> securitySchema() {
        List<SecurityScheme> apiKeys = new ArrayList<>(2);
        apiKeys.add(new ApiKey(AUTHORIZATION, AUTHORIZATION, "header"));
        return apiKeys;
    }

    /**
     * 默认的全局鉴权策略
     */
    private SecurityReference defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new SecurityReference(AUTHORIZATION, authorizationScopes);
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .version(swaggerProperties.getVersion())
                .build();
    }
}
