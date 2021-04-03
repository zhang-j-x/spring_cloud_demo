package com.jx.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.jx.user.dao")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);

    }

}
