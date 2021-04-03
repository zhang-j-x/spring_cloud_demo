package com.jx.user.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;

/**
 * springSecurity  密码加密
 * @Author: zhangjx
 * @Date: 2020/4/5 23:25
 * @Description:
 */
@Configuration
public class PasswordEncoderConfig {
    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }


    public static void main(String[] args) {
//        BCryptPasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder();
//        String encode = bCryptPasswordEncoder.encode("zjx!@#123");
//        System.out.println(encode);
//
//        String hashInfo = DigestUtils.md5Hex("18871757365@163.com" + ":" + "2021-04-16T14:34:06.599Z"
//                + ":" + "$10$KyHG9EUXOv8c2ltTGESmZuqCHeA.YGuh/VuJRYTnKUTrRwQ396BGm"
//                + ":" + "security_token_key");
//
//        String rememberCookie = Base64.getEncoder().encodeToString(("18871757365@163.com" + ":" + "2021-04-16T14:34:06.599Z" +
//                ":" + hashInfo).getBytes());
//        System.out.println("hashInfo : " + hashInfo);
//        System.out.println("rememberCookie : " + rememberCookie);
        byte[] bytes = Base64.getDecoder().decode("MTg4NzE3NTczNjUlNDAxNjMuY29tOjE2MTg1ODM2NDY1OTg6ZjM0NDA5ZmJhYmVhMmE1NWQ3ZjQwZjVlOWUxZTRhODY");

        System.out.println(new String(bytes));
    }
}
