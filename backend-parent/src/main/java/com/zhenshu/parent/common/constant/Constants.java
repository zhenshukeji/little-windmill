package com.zhenshu.parent.common.constant;


import java.math.BigDecimal;

/**
 * 通用常量信息
 *
 * @author zhenshu
 */
public class Constants {
    /**
     * 返回成功
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * 设置过期时间一天
     */
    public final static int DAY_TIME_SECOND = 86400;

    /**
     * 过期时间七天
     */
    public final static int SEVEN_DAY_SECOND = 604800;

    /**
     * 1是0否
     */
    public static final Integer NO = 0;

    /**
     * 1是0否
     */
    public static final Integer YES = 1;

    /**
     * 0
     */
    public static final int ZERO = 0;

    /**
     * 1
     */
    public static final int ONE = 1;

    public static final boolean TRUE = true;

    public static final boolean FALSE = false;

    /**
     * JWT 有效时间 8小时
     */
    public static final long JWT_EXP_TIME = 28800000;

    public static final int ADD_TIME = 30 * 60;

    /**
     * token
     */
    /**
     * JWT 密钥（ce-17 安全修复）：优先读取环境变量 JWT_SECRET，
     * 未设置时回退到原值（仅用于兼容已有部署，生产环境必须设置 JWT_SECRET）。
     */
    public static final String TOKEN_SECRET = System.getenv("JWT_SECRET") != null
            ? System.getenv("JWT_SECRET") : "${JWT_SECRET:change-me-in-production}";

    /**
     * 一分钟
     */
    public static final int ONE_HOUR = 3600;

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * 登录令牌
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 登录用户id
     */
    public static final String LOGIN_USER_ID = "login_user_id";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

}
