package com.zhenshu.parent.common.constant.exception;


import lombok.Data;

/**
 * 操作日志记录表 oper_log
 *
 * @author zhenshu
 */
@Data
public class SysOperLog {
    private static final long serialVersionUID = 1L;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求url
     */
    private String operUrl;

    /**
     * 操作地址
     */
    private String operIp;

    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 返回参数
     */
    private String jsonResult;
    /**
     * 请求头
     */
    private String header;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

}
