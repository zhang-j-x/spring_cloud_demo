package com.jx.order.service;


import com.jx.order.form.PlaceOrderForm;

public interface IOrderService {

    /**
     * 下单
     * @param order
     */
    void createOrder(PlaceOrderForm order);
}
