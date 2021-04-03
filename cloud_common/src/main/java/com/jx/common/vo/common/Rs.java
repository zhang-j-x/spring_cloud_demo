package com.jx.common.vo.common;

import java.io.Serializable;


/**
 * 统一API响应结果封装
 *
 */
public class Rs<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;
    /**
     *是否成功
     */
    private boolean success;
    /**
     * 数据
     */
    private T data;
    /**
     * 返回消息
     */
    private String msg;

    public Rs() {
    }

    private Rs(IResult result, T data) {
        this.code = result.getCode();
        this.msg = result.getMsg();
        this.success = true;
        this.data = data;
    }

    private Rs(IResult result) {
        this.success = false;
        this.code = result.getCode();
        this.msg = result.getMsg();
    }

    private Rs(IResult result, String msg) {
        this.success = false;
        this.code = result.getCode();
        this.msg = msg;
    }

    /**
     * 成功
     *
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> Rs<T> success(T data) {
        return new Rs<T>(ResultEnum.SUCCESS, data);
    }

    public static <T> Rs<T> success() {
        return new Rs<T>(ResultEnum.SUCCESS);
    }

    /**
     * 失败
     *
     * @param msg 消息
     * @return R
     */
    public static <T> Rs<T> fail(String msg) {
        return new Rs<T>(ResultEnum.FAILURE, msg);
    }


    /**
     * 失败
     *
     * @param resultCode 业务代码
     * @return R
     */
    public static <T> Rs<T> fail(IResult resultCode) {
        return new Rs<T>(resultCode);
    }


    /**
     * 无数据
     * @return R
     */
    public static <T> Rs<T> empty() {
        return new Rs<T>(ResultEnum.NO_DATA);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
