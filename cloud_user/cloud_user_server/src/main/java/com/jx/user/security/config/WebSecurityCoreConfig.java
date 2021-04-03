package com.jx.user.security.config;

import com.jx.user.config.properties.SecurityProperties;
import com.jx.user.security.filter.FilterManageSecurityConfig;
import com.jx.user.security.filter.usernamepwd.MyUserNamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Description:
 * @Author: zhangjx
 * @Date: 2021-03-17
 **/
@EnableWebSecurity(debug = true)
public class WebSecurityCoreConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Resource
    private SecurityProperties securityProperties;
    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Resource
    private MyWebAuthenticationDetailsSource  myWebAuthenticationDetailsSource;
    @Resource
    private MyInvalidSessionStrategy myInvalidSessionStrategy;

    @Resource
    private FilterManageSecurityConfig filterManageSecurityConfig;

    @Resource
    UserDetailsService userService;

    @Bean
    SessionRegistryImpl sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * 自定义UsernamePasswordAuthenticationFilter后很多属性注入无效，需要手动注入
     * @return
     * @throws Exception
     */
    @Bean
    public MyUserNamePasswordAuthenticationFilter loginFilter() throws Exception {
        MyUserNamePasswordAuthenticationFilter userNamePasswordAuthenticationFilter = new MyUserNamePasswordAuthenticationFilter();
        //设置登录成功处理器
        userNamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        //设置登录失败处理器
        userNamePasswordAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        //设置认证管理器  选择WebSecurityConfigurerAdapter实现的
        userNamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        //设置自定义的DetailSource
        userNamePasswordAuthenticationFilter.setAuthenticationDetailsSource(myWebAuthenticationDetailsSource);
        //设置登录url
        userNamePasswordAuthenticationFilter.setFilterProcessesUrl(securityProperties.getBrowser().getLoginUrl());
        //设置登录用户名名
        userNamePasswordAuthenticationFilter.setUsernameParameter(securityProperties.getBrowser().getLoginUserNameParamName());
        //设置登录密码参数名
        userNamePasswordAuthenticationFilter.setPasswordParameter(securityProperties.getBrowser().getLoginUserPasswordParamName());
        userNamePasswordAuthenticationFilter.setRememberMeServices(persistentTokenBasedRememberMeServices());
        return userNamePasswordAuthenticationFilter;
    }

//    @Bean
//    public TokenBasedRememberMeServices tokenBasedRememberMeServices(){
//        TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices("security_token_key", userService);
//        //前端参数名
//        tokenBasedRememberMeServices.setParameter(securityProperties.getBrowser().getLoginRememberMeParamName());
//        //设置过期时间
//        tokenBasedRememberMeServices.setTokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds());
//        return tokenBasedRememberMeServices;
//    }

    @Bean
    public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices(){
        JdbcTokenRepositoryImpl persistentTokenRepository = new JdbcTokenRepositoryImpl();
        persistentTokenRepository.setDataSource(dataSource);
        PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices = new PersistentTokenBasedRememberMeServices(
                "security_token_key", userService,persistentTokenRepository);
        //前端参数名
        persistentTokenBasedRememberMeServices.setParameter(securityProperties.getBrowser().getLoginRememberMeParamName());
        //设置过期时间
        persistentTokenBasedRememberMeServices.setTokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds());
        persistentTokenBasedRememberMeServices.setAlwaysRemember(false);
        return persistentTokenBasedRememberMeServices;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class)
            .apply(filterManageSecurityConfig).and()
            .authorizeRequests(req -> req.antMatchers("/user/getKey","/validateCode/getImageValidateCode")
                    .permitAll().anyRequest().authenticated())
            .rememberMe(remember -> remember.rememberMeServices(persistentTokenBasedRememberMeServices()))
            .sessionManagement().invalidSessionStrategy(myInvalidSessionStrategy).and()
            .csrf(csrf -> csrf.disable());
    }



}
