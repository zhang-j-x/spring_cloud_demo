package com.jx.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description:
 * @Author: zhangjx
 * @Date: 2021-01-28
 **/
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.jx.product.dao")
public class CloudProductServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudProductServerApplication.class, args);
    }

}
