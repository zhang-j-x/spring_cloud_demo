package com.jx.user.enums;

import com.jx.common.vo.common.IResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessRsEnum implements IResult {

    /**
     *
     */
    SUCCESS(0,"操作成功"),
    /**
     *
     */
    FAIL(-1,"操作失败"),


    /**
     *
     */
    INNER_ERROR(-2,"服务器异常，请联系管理员！");

    private Integer code;
    private String msg;



}
