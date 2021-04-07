package com.jx.user.security.config;

import com.jx.common.vo.common.Rs;
import com.jx.user.config.properties.SecurityProperties;
import com.jx.user.enums.LoginRcEnum;
import com.jx.user.security.filter.FilterManageSecurityConfig;
import com.jx.user.security.filter.usernamepwd.MyUserNamePasswordAuthenticationFilter;
import com.jx.user.util.ResponseWriterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.session.*;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

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
    private FilterManageSecurityConfig filterManageSecurityConfig;

    @Resource
    UserDetailsService userService;

    @Resource
    MyLogoutSuccessHandler logoutSuccessHandler;

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
        userNamePasswordAuthenticationFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
        return userNamePasswordAuthenticationFilter;
    }


    /**
     * 散列算法实现的记住我功能
     */
//    @Bean
//    public TokenBasedRememberMeServices tokenBasedRememberMeServices(){
//        TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices("security_token_key", userService);
//        //前端参数名
//        tokenBasedRememberMeServices.setParameter(securityProperties.getBrowser().getLoginRememberMeParamName());
//        //设置过期时间
//        tokenBasedRememberMeServices.setTokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds());
//        return tokenBasedRememberMeServices;
//    }


    /**
     * 数据库持久化令牌实现的记住我功能
     */
    @Bean
    public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices(){
        JdbcTokenRepositoryImpl persistentTokenRepository = new JdbcTokenRepositoryImpl();
        persistentTokenRepository.setDataSource(dataSource);
        //没有指定时key为一个UUID字符串，重启服务后，key重新生成，重启后所有的自动登录cookie失效。
        // 分布式多实例部署下，由于实例间key不同，当已认证用户访问另一个实例时，自动登录策略失效
        PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices = new PersistentTokenBasedRememberMeServices(
                "security_token_key", userService,persistentTokenRepository);
        //前端参数名
        persistentTokenBasedRememberMeServices.setParameter(securityProperties.getBrowser().getLoginRememberMeParamName());
        //设置过期时间
        persistentTokenBasedRememberMeServices.setTokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds());
        persistentTokenBasedRememberMeServices.setAlwaysRemember(false);
        return persistentTokenBasedRememberMeServices;
    }


    /**
     * 重写UsernamePasswordAuthenticationFilter后AbstractAuthenticationProcessingFilter 中通过http.sessionManagement()配置的
     * sessionStrategy不生效，所以手动注入到UsernamePasswordAuthenticationFilter中
     * @return
     */
    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy(){
        List<SessionAuthenticationStrategy> delegateStrategies = new ArrayList<>();
        /**
         * 并发会话控制 使最早的会话失效 然后交由ConcurrentSessionFilter处理
         */
        ConcurrentSessionControlAuthenticationStrategy  concurrentSessionControlAuthenticationStrategy =new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        concurrentSessionControlAuthenticationStrategy.setMaximumSessions(1);
        delegateStrategies.add(concurrentSessionControlAuthenticationStrategy);

        /**
         * 用户登录后会话策略  未重写UsernamePasswordAuthenticationFilter时默认策略为migrateSession
         * none:不做任何改动
         * newSession: 登录后创建一个新的session
         * migrateSession: 登录后创建一个新的session，并将旧的session中的数据复制过来
         * changeSessionId:不创建新的会话，而是使用Servlet容器提供的会话固定保护
         */
        SessionFixationProtectionStrategy sessionFixationProtectionStrategy = new SessionFixationProtectionStrategy();
        delegateStrategies.add(sessionFixationProtectionStrategy);

        /**
         * 注册seesion 通过 SessionRegistryImpl管理
         */
        RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy = new RegisterSessionAuthenticationStrategy(sessionRegistry());
        delegateStrategies.add(registerSessionAuthenticationStrategy);

        SessionAuthenticationStrategy sessionAuthenticationStrategy = new CompositeSessionAuthenticationStrategy(delegateStrategies);
        return  sessionAuthenticationStrategy;
    }




    /**
     * 是通过监听 session 的销毁事件，来及时的清理 session 的记录。用户从不同的浏览器登录后，
     * 都会有对应的 session，当用户注销登录之后，session 就会失效，但是默认的失效是通过调用
     * StandardSession#invalidate 方法来实现的，这一个失效事件无法被 Spring 容器感知到，
     * 进而导致当用户注销登录之后，Spring Security 没有及时清理会话信息表
     *
     * 提供一个 HttpSessionEventPublisher ，这个类实现了 HttpSessionListener 接口，
     * 在该 Bean 中，可以将 session 创建以及销毁的事件及时感知到，
     * 并且调用 Spring 中的事件机制将相关的创建和销毁事件发布出去，进而被 Spring Security 感知到
     * @return
     */
    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


    /**
     * 重写定义ConcurrentSessionFilter 中session失效策略
     * @return
     */
    @Bean
    ConcurrentSessionFilter concurrentSessionFilter(){
        SessionInformationExpiredStrategy sessionInformationExpiredStrategy = (event) -> {
            HttpServletResponse resp = event.getResponse();
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            ResponseWriterUtil.writeResponse(resp, Rs.fail(LoginRcEnum.LOGIN_FORCE_OFFLINE));
        };
        ConcurrentSessionFilter concurrentSessionFilter = new ConcurrentSessionFilter(sessionRegistry(),sessionInformationExpiredStrategy);
        return concurrentSessionFilter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterAt(concurrentSessionFilter(),ConcurrentSessionFilter.class)
            .apply(filterManageSecurityConfig).and()
            .authorizeRequests(req -> req.antMatchers("/user/getKey","/validateCode/getImageValidateCode")
                    .permitAll().anyRequest().authenticated())
            .rememberMe(remember -> remember.rememberMeServices(persistentTokenBasedRememberMeServices()))
            .logout(logout ->
                    logout.logoutUrl(securityProperties.getBrowser().getLogoutUrl())
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
            )
            .sessionManagement(
                    session -> session.sessionAuthenticationStrategy(sessionAuthenticationStrategy())
            )
            .csrf(csrf -> csrf.disable());
    }



}
