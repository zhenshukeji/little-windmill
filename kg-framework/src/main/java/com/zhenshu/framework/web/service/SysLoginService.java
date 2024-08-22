package com.zhenshu.framework.web.service;

import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.core.domain.model.LoginUser;
import com.zhenshu.common.core.redis.RedisCache;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.exception.user.CaptchaException;
import com.zhenshu.common.exception.user.CaptchaExpireException;
import com.zhenshu.common.exception.user.UserPasswordNotMatchException;
import com.zhenshu.common.utils.DateUtils;
import com.zhenshu.common.utils.MessageUtils;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.ServletUtils;
import com.zhenshu.common.utils.ip.IpUtils;
import com.zhenshu.framework.manager.AsyncManager;
import com.zhenshu.framework.manager.factory.AsyncFactory;
import com.zhenshu.system.business.ruoyi.service.ISysConfigService;
import com.zhenshu.system.business.ruoyi.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Resource
    private SysPermissionService sysPermissionService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        boolean captchaOnOff = configService.selectCaptchaOnOff();
        // 验证码开关
        if (captchaOnOff) {
            validateCaptcha(username, code, uuid);
        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }

    /**
     * 切换用户登录身份
     *
     * @param identity      切换的身份
     * @param associationId 关联id
     * @return 新的token
     */
    public String switchIdentity(LoginIdentity identity, Long associationId) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        user.setIdentity(identity);
        if (LoginIdentity.isBloc(identity)) {
            user.setBlocId(associationId);
            user.setKgId(null);
        } else if (LoginIdentity.isKg(identity)) {
            user.setKgId(associationId);
        }
        loginUser.setPermissions(sysPermissionService.getMenuPermission(user));
        return tokenService.createToken(loginUser);
    }
}
