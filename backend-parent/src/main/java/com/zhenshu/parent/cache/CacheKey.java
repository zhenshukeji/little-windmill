package com.zhenshu.parent.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author jing
 * @version 1.0
 * @desc redis缓存key类
 * @date 2020/6/11 0011 13:50
 **/
@Component
public class CacheKey {

    @Value("${spring.redis.cache_name}")
    public String cacheName;

    /**
     * 获取登录的缓存
     */
    public String getLoginKey(String token) {
        return String.format("%s:login_token:%s", cacheName, token);
    }
}
