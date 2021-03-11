package com.jx.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jx.product.entity.Product;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author zhangjx
 * @since 2021-01-26
 */
public interface ProductMapper extends BaseMapper<Product> {

    Integer updateProductStock(@Param("productId") Long productId, @Param("count") Integer count);
}
