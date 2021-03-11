package com.jx.order.form;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: zhangjx
 * @Date: 2021-01-26 15:34
 **/
@Data
public class PlaceOrderForm {
    /**商品列表*/
    private List<OrderForm> orderList;
    /**用户*/
    private String userId;
    /**电话*/
    private String phoneNumber;
    /**配送地址*/
    private String shippingAddress;
}
