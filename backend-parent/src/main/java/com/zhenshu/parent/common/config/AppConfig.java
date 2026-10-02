package com.zhenshu.parent.common.config;

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
@ConfigurationProperties(prefix = "parent-config")
public class AppConfig {

    /**
     * 上传路径
     */
    private static String profile;

    /**
     * 用户
     */
    private User user;

    /**
     * 上传图片后缀限制
     */
    private List<String> uploadLimit;

    private String uploadPath;

    private String fileUrlPrefix;

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


    public static String getProfile() {
        return profile;
    }

    public static void setProfile(String profile) {
        AppConfig.profile = profile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath() {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getFileUrlPrefix() {
        return fileUrlPrefix;
    }

    public void setFileUrlPrefix(String fileUrlPrefix) {
        this.fileUrlPrefix = fileUrlPrefix;
    }

    public static String getUploadPathStatic() {
        return getProfile() + "/upload";
    }
}
