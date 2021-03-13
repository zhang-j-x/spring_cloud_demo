package com.jx.product.service;


import com.jx.product.entity.Product;

public interface IProductService {

    /**
     * 查询产品详情
     * @param productId
     */
    Product getProductInfo(Long  productId);

    /**
     *
     * @param productId
     * @param stock
     */
    void updateProductInfo( Long productId, Integer stock);
}
