package com.jx.user.security.filter;

import com.jx.user.security.filter.param.HandleSecurityParamFilter;
import com.jx.user.security.filter.validatecode.ImageValidateCodeFilter;
import com.jx.user.security.filter.validatecode.SmsValidateCodeFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhangjx
 * @Date: 2021-03-17
 **/
@Component
public class FilterManageSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Resource
    private ImageValidateCodeFilter validateCodeFilter;
    @Resource
    private SmsValidateCodeFilter smsCodeFilter;
    @Resource
    HandleSecurityParamFilter handleSecurityParamFilter;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(handleSecurityParamFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }
}