package com.jx.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Author: zhangjx
 * @Date: 2021-02-02
 **/
@Slf4j
@Component
public class TimeFilter implements GatewayFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        StopWatch timer = new StopWatch();
        timer.start(exchange.getRequest().getURI().getRawPath());
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    timer.stop();
                    log.info(timer.prettyPrint());
                })

        );
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
