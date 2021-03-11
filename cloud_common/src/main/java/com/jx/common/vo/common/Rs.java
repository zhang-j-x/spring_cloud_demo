package com.jx.common.vo.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


/**
 * 统一API响应结果封装
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
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


    private Rs(com.jx.product.vo.common.IResult resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
        this.success = true;
        this.data = data;
    }

    private Rs(com.jx.product.vo.common.IResult resultCode) {
        this.success = false;
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
    }

    private Rs(com.jx.product.vo.common.IResult resultCode, String msg) {
        this.success = false;
        this.code = resultCode.getCode();
        this.msg = msg;
    }

    /**
     * 成功
     *
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> Rs<T> success(T data) {
        return new Rs<T>(com.jx.product.vo.common.ResultEnum.SUCCESS, data);
    }

    public static <T> Rs<T> success() {
        return new Rs<T>(com.jx.product.vo.common.ResultEnum.SUCCESS);
    }

    /**
     * 失败
     *
     * @param msg 消息
     * @return R
     */
    public static <T> Rs<T> fail(String msg) {
        return new Rs<>(com.jx.product.vo.common.ResultEnum.FAILURE, msg);
    }


    /**
     * 失败
     *
     * @param resultCode 业务代码
     * @return R
     */
    public static <T> Rs<T> fail(com.jx.product.vo.common.IResult resultCode) {
        return new Rs<>(resultCode);
    }


    /**
     * 无数据
     * @return R
     */
    public static <T> Rs<T> empty() {
        return new Rs<>(com.jx.product.vo.common.ResultEnum.NO_DATA);
    }

}
