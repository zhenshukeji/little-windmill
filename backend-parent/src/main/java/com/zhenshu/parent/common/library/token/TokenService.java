package com.zhenshu.parent.common.library.token;

import cn.hutool.core.util.IdUtil;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.cache.CachesManages;
import com.zhenshu.parent.common.constant.Constants;
import com.zhenshu.parent.common.utils.GsonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jing
 * @version 1.0
 * @desc 登录
 * @date 2022/5/7 0007 15:05
 **/
@Component
public class TokenService extends CachesManages {

    @Value("${token.header}")
    private String header;

    @Value("${token.secret}")
    private String secret;

    @javax.annotation.PostConstruct
    public void debugSecret() {
        System.out.println("[DEBUG] token.secret resolved to: [" + secret + "] length=" + (secret == null ? -1 : secret.length()));
    }

    @Value("${token.expireTime}")
    private int expireTime;

    /**
     * 创建令牌
     *
     * @param data 需要保存的用户信息
     * @return 令牌
     */
    public String createToken(LoginUser data) {
        String uuid = IdUtil.fastUUID();
        String userId = data.getMiniLogin().getId().toString();
        String loginKey = cacheKeyManager.getLoginKey(userId);
        data.setCertificate(uuid);
        String jsonData = GsonUtil.GsonString(data);
        cache.set(loginKey, jsonData, expireTime);

        Map<String, Object> claims = new HashMap<>(2);
        claims.put(Constants.LOGIN_USER_KEY, uuid);
        claims.put(Constants.LOGIN_USER_ID, userId);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取请求token
     *
     * @param request 请求
     * @return token
     */
    private String getToken(NativeWebRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        // 根据uuid将loginUser缓存
        String userId = loginUser.getMiniLogin().getId().toString();
        String loginKey = cacheKeyManager.getLoginKey(userId);
        cache.set(loginKey, GsonUtil.GsonString(loginUser), expireTime);
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginData(NativeWebRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            // 解析对应的权限以及用户信息
            String userId = (String) claims.get(Constants.LOGIN_USER_ID);
            String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            String loginKey = cacheKeyManager.getLoginKey(userId);
            String data = cache.get(loginKey);
            LoginUser loginUser = null;
            if (StringUtils.isNotEmpty(data)) {
                loginUser = GsonUtil.GsonToBean(data, LoginUser.class);
            }
            // 判断与此次登录信息是否相符
            if (StringUtils.isNotEmpty(uuid) && loginUser != null && uuid.equals(loginUser.getCertificate())) {
                return loginUser;
            }
        }
        return null;
    }

}
