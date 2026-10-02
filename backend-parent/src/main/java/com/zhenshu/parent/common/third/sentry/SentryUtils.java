package com.zhenshu.parent.common.third.sentry;

import io.sentry.SentryClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jing
 * @version 1.0
 * @desc sentry 工具
 * @date 2021/5/10 0010 14:48
 **/
@Service
public class SentryUtils {

    @Resource
    private SentryConfig sentryConfig;

    @Resource
    private SentryClient sentry;

    private Log log = LogFactory.getLog(SentryUtils.class);


    /**
     * 发送消息
     *
     * @param message 消息
     */
    public void sendMessage(String message) {
        // sentry发送
        if (sentryConfig.isEnable()) {
            log.warn(message);
            // 捕获异常
            SentryException exception = new SentryException(message);
            sentry.sendException(exception);
        }
    }


    /**
     * 发送消息
     *
     * @param e 消息
     */
    public void sendException(Exception e) {
        // sentry发送
        if (sentryConfig.isEnable()) {
            // 捕获异常
            sentry.sendException(e);
        }
    }

}
