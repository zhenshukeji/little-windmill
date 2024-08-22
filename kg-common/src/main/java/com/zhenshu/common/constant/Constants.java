package com.zhenshu.common.constant;

import io.jsonwebtoken.Claims;

import java.time.LocalTime;

/**
 * 通用常量信息
 *
 * @author ruoyi
 */
public class Constants {
    /**
     * 一小时
     */
    public static final int ONE_HOUR = 3600;

    /**
     * 设置过期时间一天
     */
    public final static int DAY_TIME_SECOND = 86400;

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = Claims.SUBJECT;

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi:";

    /**
     * LDAP 远程方法调用
     */
    public static final String LOOKUP_LDAP = "ldap:";

    /**
     * LDAPS 远程方法调用
     */
    public static final String LOOKUP_LDAPS = "ldaps:";

    /**
     * 定时任务白名单配置（仅允许访问的包名，如其他需要可以自行添加）
     */
    public static final String[] JOB_WHITELIST_STR = {"com.ruoyi"};

    /**
     * 定时任务违规的字符
     */
    public static final String[] JOB_ERROR_STR = {"java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
            "org.springframework", "org.apache"};

    /**
     * true
     */
    public static final Boolean TRUE = true;

    /**
     * false
     */
    public static final Boolean FALSE = false;

    /**
     * 若依删除标志-存在
     */
    public static final String RY_DEL_FLAG_EXIST = "0";

    /**
     * 若依删除标志-删除
     */
    public static final String RY_DEL_FLAG_DELETE = "2";

    /**
     * 正常状态
     */
    public static final String RY_NORMAL = "0";

    /**
     * 封禁状态
     */
    public static final String RY_DISABLE = "1";

    /**
     * 数字10
     */
    public static final int TEN = 10;

    /**
     * 数字100
     */
    public static final int ONE_HUNDRED = 100;

    /**
     * 数字一
     */
    public static final int ONE = 1;

    /**
     * 数字二
     */
    public static final int TWO = 2;

    /**
     * 数字零
     */
    public static final int ZERO = 0;

    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 14;

    /**
     * sys_role表设置bind_count的sql片段
     */
    public static final String INCR_ROLE_BIND_COUNT = "bind_count = bind_count + 1";
    public static final String DECR_ROLE_BIND_COUNT = "bind_count = bind_count - 1";
    public static final String DECR_BY_ROLE_BIND_COUNT = "bind_count = bind_count - %d";

    /**
     * kg_bloc表设置kg_count的sql片段
     */
    public static final String INCR_BLOC_KG_COUNT = "kg_count = kg_count + 1";
    public static final String DECR_BLOC_KG_COUNT = "kg_count = kg_count - 1";

    /**
     * kg_kindergarten表设置staff_count的sql片段
     */
    public static final String INCR_KG_STAFF_COUNT = "staff_count = staff_count + 1";
    public static final String DECR_KG_STAFF_COUNT = "staff_count = staff_count - 1";

    /**
     * kg_kindergarten表设置student_count的sql片段
     */
    public static final String INCR_KG_STUDENT_COUNT = "student_count = student_count + 1";
    public static final String DECR_KG_STUDENT_COUNT = "student_count = student_count - 1";
    public static final String INCR_BY_KG_STUDENT_COUNT = "student_count = student_count + %d";

    /**
     * kg_classroom表设置student_count的sql片段
     */
    public static final String INCR_CLASS_STUDENT_COUNT = "student_count = student_count + 1";
    public static final String DECR_CLASS_STUDENT_COUNT = "student_count = student_count - 1";
    public static final String INCR_BY_CLASS_STUDENT_COUNT = "student_count = student_count + %d";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "abcd1234";

    /**
     * 导入excel最大条数
     */
    public static final int EXCEL_MAX_SIZE = 1024;

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    /**
     * 批量插入的最大阈值
     */
    public static final int SAVE_BATCH_MAX = 30;

    /**
     * 默认的上班时间
     */
    public static final LocalTime OFFICE_HOUR = LocalTime.of(8, 0, 0);

    /**
     * 默认的下班时间
     */
    public static final LocalTime CLOSING_HOUR = LocalTime.of(17, 0, 0);
}
