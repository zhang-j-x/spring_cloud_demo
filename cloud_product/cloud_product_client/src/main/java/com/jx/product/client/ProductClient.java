package com.jx.product.client;

import com.jx.common.vo.common.Rs;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@EnableFeignClients(basePackages = "com.jx.product.client")
@FeignClient(name = "product")
public interface ProductClient {

    @RequestMapping("/product/serverPort")
    Rs serverPort();

    @GetMapping("updateProductStock")
     Rs updateProductStock(@RequestParam("productId")Long productId,@RequestParam("stock") Integer stock);
}
