package com.jx.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author zhangjx
 * @since 2021-01-26
 */
@Data
public class OrderItem extends Model<OrderItem> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单明细表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 产品id
     */
    @TableField("product_id")
    private Long productId;

    /**
     * 购买数量
     */
    @TableField("purchase_count")
    private Integer purchaseCount;

    /**
     * 支付金额
     */
    @TableField("payment_amount")
    private BigDecimal paymentAmount;

    /**
     * 下单时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", productId=" + productId +
        ", purchaseCount=" + purchaseCount +
        ", paymentAmount=" + paymentAmount +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
