package com.zhenshu.common.web;


import com.zhenshu.common.constant.HttpStatus;
import com.zhenshu.common.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Result<T> {
    /**
     * 1.status状态值：代表本次请求response的状态结果。
     */
    private Integer code;
    /**
     * 2.response描述：对本次状态码的描述。
     */
    private String msg;
    /**
     * 3.data数据：本次返回的数据。
     */
    private T data;

    /**
     * 成功，创建ResResult：有data数据
     */
    public Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 成功，创建ResResult：有data数据
     */
    public Result<T> success() {
        Result<T> result = new Result<T>();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 成功，创建ResResult：有data数据
     */
    public Result<T> fail() {
        Result<T> result = new Result<T>();
        result.setResultCode(ResultCode.FAIL);
        return result;
    }

    /**
     * 传参为空
     */
    public Result<T> paramEmpty() {
        Result<T> result = new Result<T>();
        result.setResultCode(ResultCode.PARAM_EMPTY);
        return result;
    }

    /**
     * 传参为空
     */
    public Result<T> paramEmpty(String msg) {
        Result<T> result = new Result<T>();
        result.setCode(ResultCode.PARAM_EMPTY.code());
        result.setMsg(msg);
        return result;
    }

    /**
     * 失败，指定status、desc
     */
    public Result<T> fail(Integer code, String desc) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMsg(desc);
        String uri = ServletUtils.getRequest().getRequestURI();
        log.warn("接口返回状态码非 200 ， url is {},  status code is {} , msg is {}", uri, code, msg);
        return result;
    }

    /**
     * 失败，指定status、desc
     */
    public Result<T> fail(String desc) {
        Result<T> result = new Result<T>();
        result.setCode(HttpStatus.ERROR);
        result.setMsg(desc);
        String uri = ServletUtils.getRequest().getRequestURI();
        log.warn("接口返回状态码非 200 ， url is {},  status code is {} , msg is {}", uri, code, msg);
        return result;
    }

    /**
     * 失败，指定ResultCode枚举
     */
    public Result<T> fail(ResultCode resultCode) {
        Result<T> result = new Result<T>();
        result.setResultCode(resultCode);
        return result;
    }

    /**
     * 把ResultCode枚举转换为ResResult
     */
    private void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg = code.message();
    }

    /**
     * 成功，创建ResResult：有data数据
     */
    public static <T> Result<T> buildSuccess(T data) {
        Result<T> result = new Result<T>();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 成功，创建ResResult
     */
    public static Result buildSuccess() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 失败，指定status、desc
     */
    public static Result buildFail(String desc) {
        Result result = new Result();
        result.setCode(HttpStatus.ERROR);
        result.setMsg(desc);
        String uri = ServletUtils.getRequest().getRequestURI();
        log.warn("interface status code is not 200 ， url is {},  status code is {} , msg is {}", uri, HttpStatus.ERROR, desc);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

