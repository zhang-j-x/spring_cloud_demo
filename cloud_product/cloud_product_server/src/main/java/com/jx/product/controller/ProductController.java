package com.jx.product.controller;

import com.jx.common.vo.common.Rs;
import com.jx.product.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zhangjx
 * @Date: 2021-01-30
 **/
@RestController
@RequestMapping("/product/")
@Slf4j
public class ProductController {


    @Autowired
    Environment environment;

    @Autowired
    IProductService productService;

    @GetMapping("qryProductInfo")
    public Rs qryProductInfo(Long productId){
        log.info("产品详情：" + productId);
        return Rs.success(productService.getProductInfo(productId));
    }

    @GetMapping("updateProductStock")
    public Rs qryProductInfo(Long productId,Integer stock){
        productService.updateProductInfo(productId,stock);
        return Rs.success();
    }

    @GetMapping("serverPort")
    public Rs serverPort(){
        log.info("ribbonTest in product ....");
        return Rs.success(environment.getProperty("server.port"));
    }
}
