package com.jx.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jx.product.entity.enums.ProductStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author zhangjx
 * @since 2021-01-26
 */
@Data
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "product_id", type = IdType.AUTO)
    private Long productId;

    /**
     * 商品名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 商品价格
     */
    @TableField("product_price")
    private BigDecimal productPrice;

    /**
     * 商品库存
     */
    @TableField("product_stock")
    private Integer productStock;

    /**
     * 商品描述
     */
    @TableField("product_desc")
    private String productDesc;

    /**
     * 商品状态
     */
    @TableField("product_status")
    private ProductStatusEnum productStatus;

    /**
     * 商品创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 商品创建人
     */
    @TableField("create_user")
    private Integer createUser;


    @Override
    public String toString() {
        return "Product{" +
        "productId=" + productId +
        ", productName=" + productName +
        ", productPrice=" + productPrice +
        ", productStock=" + productStock +
        ", productDesc=" + productDesc +
        ", productStatus=" + productStatus +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        "}";
    }
}
