package com.jx.product.service.impl;

import com.jx.product.dao.ProductMapper;
import com.jx.product.entity.Product;
import com.jx.product.service.IProductService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhangjx
 * @Date: 2021-01-30
 **/
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Resource
    ProductMapper productMapper;

    @Override
    public Product getProductInfo(Long productId) {
        return productMapper.selectById(productId);
    }

    @Override
    public void updateProductInfo(Long productId,Integer stock) {
        log.info("全局事务id ：" + RootContext.getXID());
        System.out.println("全局事务id ：" + RootContext.getXID());
        productMapper.updateProductStock(productId,stock);
    }
}
