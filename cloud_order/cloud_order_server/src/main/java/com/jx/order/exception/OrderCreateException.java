package com.jx.order.exception;

/**
 * @Description:
 * @Author: zhangjx
 * @Date: 2021-01-26 15:55
 **/
public class OrderCreateException extends RuntimeException{
    public OrderCreateException(String message) {
        super(message);
    }
}
