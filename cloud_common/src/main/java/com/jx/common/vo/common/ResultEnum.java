package com.jx.common.vo.common;

import lombok.Getter;

/**
 * 业务代码枚举
 */
@Getter
public enum ResultEnum implements IResult{


    /**
     * 操作成功
     */
    SUCCESS(0, "操作成功"),

    /**
     * 操作失败
     */
    FAILURE(1, "操作失败"),

    /**
     * 无数据
     */
    NO_DATA(2, "无数据");

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    /**
     * code编码
     */
     private Integer code;
    /**
     * 中文信息描述
     */
    private String msg;



}
