package com.jx.product.vo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务代码枚举
 */
@Getter
@AllArgsConstructor
public enum ResultEnum implements IResult {

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

    /**
     * code编码
     */
    final int code;
    /**
     * 中文信息描述
     */
    final String message;

}
