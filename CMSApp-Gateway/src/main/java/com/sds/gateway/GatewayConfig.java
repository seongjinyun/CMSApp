package com.sds.gateway;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    @Bean
    public GlobalFilter globalFilter() {
        return (exchange, chain) -> {
            // CORS 설정
            exchange.getResponse()
                    .getHeaders()
                    .add("Access-Control-Allow-Origin", "*");
            exchange.getResponse()
                    .getHeaders()
                    .add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            exchange.getResponse()
                    .getHeaders()
                    .add("Access-Control-Allow-Headers", "*");

            // OPTIONS 요청에 대한 처리
            if (exchange.getRequest()
                    .getMethod()
                    .equals("OPTIONS")) {
                exchange.getResponse()
                        .setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }

            // 다음 필터(chain) 호출
            return chain.filter(exchange);
        };
    }
}
