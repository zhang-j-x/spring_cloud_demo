package com.jx.order.service.impl;


import com.jx.order.config.mybatis.DataSourceKey;
import com.jx.order.config.mybatis.DynamicDataSourceContextHolder;
import com.jx.order.dao.OrderItemMapper;
import com.jx.order.dao.OrderMapper;
import com.jx.order.entity.Order;
import com.jx.order.entity.OrderItem;
import com.jx.order.entity.enums.OrderStatusEnum;
import com.jx.order.form.PlaceOrderForm;
import com.jx.order.service.IOrderService;
import com.jx.product.client.ProductClient;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 */
@Service("base")
public class OrderServiceImpl implements IOrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    ProductClient productClient;


    @Override
    @GlobalTransactional(name = "order-createOrder")
    public void createOrder(PlaceOrderForm order) {
        //1、生成订单
        DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.MASTER);
        Order newOrder = new Order();
        newOrder.setCreateUser(888);
        newOrder.setOrderStatus(OrderStatusEnum.NEW);
        newOrder.setPayTime(LocalDateTime.now());
        newOrder.setCreateTime(LocalDateTime.now());

        orderMapper.insert(newOrder);
        DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.SLAVE);
        BigDecimal amount = new BigDecimal(0);

        order.getOrderList().forEach(orderForm -> {
            Long productId = orderForm.getProductId();
            Integer count = orderForm.getCount();
            //2、扣库存
            productClient.updateProductStock(productId,count);

            //3、插入订单明细
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(newOrder.getOrderId());
            orderItem.setProductId(orderForm.getProductId());
//            orderItem.setPaymentAmount(product.getProductPrice().multiply(BigDecimal.valueOf(count)));
            orderItem.setCreateTime(LocalDateTime.now());
            orderItem.setPurchaseCount(count);
            orderItemMapper.insert(orderItem);
        });


        //4、更新订单
        newOrder.setOrderAmount(amount);
        DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.MASTER);
        orderMapper.updateById(newOrder);
    }
}
