package com.zhenshu.system.cache;

import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.core.redis.RedisUtils;
import com.zhenshu.common.utils.GsonUtil;
import com.zhenshu.common.utils.StringUtils;
import com.zhenshu.system.business.kg.base.record.domain.po.MiniLogin;
import com.zhenshu.system.cache.dto.MiniLoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jing
 * @version 1.0
 * @desc 小程序用户登录缓存
 * @date 2022/7/4 0004 17:14
 **/
@Service
public class MinLoginCacheManages extends CachesManages {

    private final RedisUtils cache;
    @Value("${kgParent.redis.cache_name}")
    public String cacheName;

    @Autowired
    public MinLoginCacheManages(StringRedisTemplate kgParentRedisTemplate) {
        this.cache = new RedisUtils(kgParentRedisTemplate);
    }

    /**
     * 获取登录的缓存
     */
    public String getLoginKey(Long userId) {
        return String.format("%s:login_token:%d", cacheName, userId);
    }

    /**
     * 更新登录里的信息
     *
     * @param loginUser 登录信息
     */
    public void updateLoginUser(MiniLoginUser loginUser) {
        Long userId = loginUser.getMiniLogin().getId();
        String loginKey = this.getLoginKey(userId);
        long expireTime = cache.getExpire(loginKey);
        if (expireTime <= Constants.ZERO) {
            return;
        }
        cache.set(loginKey, GsonUtil.GsonString(loginUser), expireTime);
    }

    /**
     * 获取小程序登录用户信息
     *
     * @param userId 用户id
     */
    public MiniLoginUser getLoginUser(Long userId) {
        String loginKey = this.getLoginKey(userId);
        String data = cache.get(loginKey);
        if (StringUtils.isEmpty(data)) {
            return null;
        } else {
            return GsonUtil.GsonToBean(data, MiniLoginUser.class);
        }
    }

    /**
     * 获取多个小程序登录用户信息
     *
     * @param userIds 用户id集合
     */
    public List<MiniLoginUser> multiGetLoginUser(List<Long> userIds) {
        List<String> cacheKeys = userIds.stream().map(this::getLoginKey).collect(Collectors.toList());
        List<String> dataList = cache.multiGet(cacheKeys);
        if (!CollectionUtils.isEmpty(dataList)) {
            dataList = dataList.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(dataList)) {
            return Collections.emptyList();
        } else {
            return dataList.stream().map(data -> GsonUtil.GsonToBean(data, MiniLoginUser.class)).collect(Collectors.toList());
        }
    }
}
