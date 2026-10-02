package com.zhenshu.parent.common.library.email;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jing
 * @version 1.0
 * @desc email 配置
 * @date 2021/8/24 0024 10:03
 **/
@Component
@Data
@ConfigurationProperties(prefix = "email-config")
public class EmailConfig {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String myEmailHost;

    /**
     * 发件人
     */
    private String sender;
}
