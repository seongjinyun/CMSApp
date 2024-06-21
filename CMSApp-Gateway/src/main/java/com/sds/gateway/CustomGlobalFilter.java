package com.sds.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Order(1) // Order를 조정하여 실행 순서를 조정할 수 있습니다.
public class CustomGlobalFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Pre Filter Logic
        ServerHttpRequest request = exchange.getRequest();

        // 요청 URI 추출
        String uri = request.getURI().toString();
        log.debug("Request URI: " + uri);

        // 요청 파라미터 추출
        request.getQueryParams().forEach((param, values) -> log.debug("Request Parameter: " + param + " = " + values));

        // 헤더 정보 추출
        request.getHeaders().forEach((header, values) -> log.debug("Request Header: " + header + " = " + values));

        // Post Filter Logic
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.debug("Response status code: " + exchange.getResponse().getStatusCode());

            // 라우트 정보 추출
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            if (route != null) {
                log.debug("Matched Route ID: " + route.getId());
                log.debug("Matched Route URI: " + route.getUri());
            }
        }));
    }
}
