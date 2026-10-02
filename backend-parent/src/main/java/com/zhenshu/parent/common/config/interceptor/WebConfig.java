package com.zhenshu.parent.common.config.interceptor;

import com.zhenshu.parent.common.config.AppConfig;

import com.zhenshu.parent.common.config.aspect.login.LoginUserHandlerResolver;
import com.zhenshu.parent.common.config.aspect.login.LoginStudentIdHandlerResolver;
import com.zhenshu.parent.common.library.knife4j.conver.IntegerCodeToEnumConverterFactory;
import com.zhenshu.parent.common.library.knife4j.conver.StringCodeToEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yuxi
 * @version 1.0
 * @date 2020/12/20 20:55
 * @desc config
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private AuthenticationInterceptor interceptor;

    @javax.annotation.Resource
    private AppConfig appConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器，配置拦截地址
        registry.addInterceptor(interceptor).addPathPatterns("/bk/**", "/cdn/upload").excludePathPatterns("/bk/login");
    }


    /**
     * 社区版自托管文件服务（ce-10）：/files/** 映射到本地上传目录，公开读取
     * （与原 OSS 公开 CDN URL 语义一致）。
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:" + java.nio.file.Paths.get(appConfig.getUploadPath()).toAbsolutePath().normalize() + "/");
    }

    /**
     * 枚举类的转换器工厂 addConverterFactory，用来做SpringBoot入参的转换
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new IntegerCodeToEnumConverterFactory());
        registry.addConverterFactory(new StringCodeToEnumConverterFactory());
    }

    /**
     * 参数转换类
     * @param argumentResolvers 参数
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LoginStudentIdHandlerResolver());
        argumentResolvers.add(new LoginUserHandlerResolver());
    }
}
