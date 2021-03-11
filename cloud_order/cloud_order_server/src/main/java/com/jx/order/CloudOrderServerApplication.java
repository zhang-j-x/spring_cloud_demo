package com.jx.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description:
 * @Author: zhangjx
 * @Date: 2021-01-28
 **/
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.jx.order.dao")
//@RibbonClient(name = "product",configuration = MyRibbonRule.class)
@EnableFeignClients(basePackages = "com.jx.product.client")
public class CloudOrderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudOrderServerApplication.class, args);
    }

}
