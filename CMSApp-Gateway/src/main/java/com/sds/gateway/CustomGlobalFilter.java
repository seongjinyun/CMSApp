package com.sds.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
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
@Order(0)
public class CustomGlobalFilter implements GlobalFilter {
	
	@Autowired
	private RouteLocator routeLocator;

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

		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			// Post Filter Logic
			log.debug("Response status code: " + exchange.getResponse().getStatusCode());
			System.out.println("Response status code: " + exchange.getResponse().getStatusCode());
			
			
			//라우드 정보 추출
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            if (route != null) {
                System.out.println("Matched Route ID: " + route.getId());
                System.out.println("Matched Route URI: " + route.getUri());
            }
            
			
		}));

	}

}