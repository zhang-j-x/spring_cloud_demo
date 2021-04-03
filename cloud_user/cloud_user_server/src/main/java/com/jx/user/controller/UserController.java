package com.jx.user.controller;

import com.jx.common.vo.common.Rs;
import com.jx.user.util.RsaEncryptUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhangjx
 * @Date: 2021-03-17
 **/
@RestController
@RequestMapping("/user/")
public class UserController {

    @Resource
    RsaEncryptUtil rsaEncryptUtil;
    /***
     * 登录后如何拿到用户信息
     *
     */
    @GetMapping("getLoginUserInfo")
    public Authentication getLoginUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
        return authentication;
    }

    /**
     * 获取RSA加密公钥
     * @return
     */
    @GetMapping("getKey")
    public Rs getKey(){
        return Rs.success(rsaEncryptUtil.getPublicKey());
    }
}
