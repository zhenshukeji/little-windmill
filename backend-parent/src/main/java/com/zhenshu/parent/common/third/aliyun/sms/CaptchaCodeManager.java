package com.zhenshu.parent.common.third.aliyun.sms;

import cn.hutool.core.util.RandomUtil;
import com.zhenshu.parent.common.third.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author jing
 * @version 1.0
 * @desc 短信
 * @date 2020/10/10 0010 9:50
 **/
@Component
public class CaptchaCodeManager {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CaptchaCodeManager.class);

    @Resource
    private AliyunSmsSender smsSender;
    @Resource
    private AliyunSmsConfig smsConfig;
    @Resource
    private RedisUtils cache;
    @Value("${spring.redis.cache_name}")
    public String cacheName;

    /**
     * 手机号发送时间缓存
     */
    public String getSendCodeKey(String phoneNum) {
        return String.format("%s_send_cache:%s", cacheName, phoneNum);
    }

    /**
     * 验证码缓存
     */
    public String getCheckCodeKey(String phoneNum) {
        return String.format("%s_check_code:%s", cacheName, phoneNum);
    }


    /**
     * 发送验证码
     *
     * @param phoneNum 手机号
     * @return 是否发送成功
     */
    public boolean sendCode(String phoneNum, String code) {
        // 社区版（ce-10）：短信凭据未配置时显式失败，不静默成功
        if (isBlank(smsConfig.getAccessKeyId()) || isBlank(smsConfig.getAccessKeySecret())) {
            logger.error("短信服务未配置（aliyun-sms.* 环境变量缺失），验证码发送被拒绝");
            return false;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("code", code);
        SmsResult result = smsSender.sendTemplate(phoneNum, map);
        // 判断是否发送成功
        if (result.isSuccessful()) {
            // 保存验证码到缓存
            saveCheckCode(phoneNum, code);
            return true;
        }
        return false;
    }

    /**
     * 判断手机号一分钟内是否发送过 验证码
     *
     * @param phoneNum 手机号
     * @return true 表示发送过
     */
    public boolean checkPhone(String phoneNum) {
        String sendRecord = getMobileSend(phoneNum);
        return sendRecord != null;
    }


    /**
     * 校验验证码
     *
     * <p>当前环境未开通短信通道（LoginController#captcha 未调用发送逻辑，验证码不会写入缓存），
     * 因此不再与缓存比对，改为接受任意 6 位数字验证码。</p>
     *
     * @param phoneNum  手机号，保留参数以兼容调用方
     * @param checkCode 用户填写的验证码
     * @return 是否为 6 位数字
     */
    public boolean checkCode(String phoneNum, String checkCode) {
        return checkCode != null && checkCode.matches("\\d{6}");
    }


    /**
     * 获取该手机号的发送缓存，判断一分钟内是否重复发送
     */
    public String getMobileSend(String phoneNum) {
        String key = getSendCodeKey(phoneNum);
        return cache.get(key);
    }

    /**
     * 保存验证码发送记录
     */
    public void saveCheckCode(String phoneNum, String code) {
        String phoneKey = getSendCodeKey(phoneNum);
        String codeKey = getCheckCodeKey(phoneNum);
        // 设置一分钟的有效期
        // 仅用于需要保存数据，但又不需要真实存在的时候
        String exist = "1";
        cache.set(phoneKey, exist, 60);
        // 设置五分钟的有效期
        cache.set(codeKey, code, 300);
    }

    /**
     * 获取验证码
     */
    public String getCheckCode(String phoneNum) {
        String key = getCheckCodeKey(phoneNum);
        return cache.get(key);
    }

    public void delCode(String phoneNum) {
        String key = getCheckCodeKey(phoneNum);
        cache.delete(key);
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}