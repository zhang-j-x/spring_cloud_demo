package com.jx.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jx.order.entity.enums.OrderStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangjx
 * @since 2021-01-26
 */
@Data
@TableName("`order`")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    /**
     * 订单金额
     */
    @TableField("order_amount")
    private BigDecimal orderAmount;

    /**
     * 订单状态
     */
    @TableField("order_status")
    private OrderStatusEnum orderStatus;

    /**
     * 下单人
     */
    @TableField("create_user")
    private Integer createUser;

    /**
     * 下单时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 支付时间
     */
    @TableField("pay_time")
    private LocalDateTime payTime;

    /**
     * 更改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;




    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }

    @Override
    public String toString() {
        return "Order{" +
        "orderId=" + orderId +
        ", orderAmount=" + orderAmount +
        ", orderStatus=" + orderStatus +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", payTime=" + payTime +
        ", updateTime=" + updateTime +
        "}";
    }


}
