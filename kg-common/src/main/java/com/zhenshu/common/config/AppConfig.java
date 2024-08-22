package com.zhenshu.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yuxi
 * @version 1.0
 * @date 2021/2/4 22:09
 * @desc
 */
@Component
@ConfigurationProperties(prefix = "app-config")
public class AppConfig {

    /**
     * 上传路径
     */
    private String profile;

    /**
     * 用户
     */
    private User user;

    /**
     * 上传图片后缀限制
     */
    private List<String> uploadLimit;

    public List<String> getUploadLimit() {
        return uploadLimit;
    }

    public void setUploadLimit(List<String> uploadLimit) {
        this.uploadLimit = uploadLimit;
    }

    public static class User {
        private String username;

        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 获取下载路径
     */
    public String getDownloadPath() {
        return this.getProfile();
    }
}
