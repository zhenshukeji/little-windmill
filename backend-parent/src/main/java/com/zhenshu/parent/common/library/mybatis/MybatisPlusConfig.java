package com.zhenshu.parent.common.library.mybatis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author jing
 * @version 1.0
 * @desc
 * @date 2021/8/24 0024 10:12
 **/
@Component
@Data
public class MybatisPlusConfig {

    @Value("${spring.datasource.druid.url}")
    private String dbUrl;

    @Value("${spring.datasource.druid.username}")
    private String userName;

    @Value("${spring.datasource.druid.password}")
    private String password;

}
