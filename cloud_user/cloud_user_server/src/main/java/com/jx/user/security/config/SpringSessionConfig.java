package com.jx.user.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * HttpSessionListener的Bean和Spring Session Redis自动配置出来的Bean在同一个配置类中，导致循环引用。
 * 所以单独配置
 * @Description:
 * @Author: zhangjx
 * @Date: 2021-04-08
 **/
@EnableRedisHttpSession
@Configuration
public class SpringSessionConfig {


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

}
