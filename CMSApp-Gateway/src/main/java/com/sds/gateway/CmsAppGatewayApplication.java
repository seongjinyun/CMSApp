package com.sds.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class CmsAppGatewayApplication {
	
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
    	        .route("CMSApp-Document", r -> r.path("/file/**")
    	        		.filters(f -> f.stripPrefix(1)).uri("http://localhost:9997"))
    	        .route("CMSApp-Setting", r -> r.path("/setting/**")
    	        		.filters(f -> f.stripPrefix(1)).uri("http://localhost:9996"))
    	        .route("CMSApp-Client", r -> r.path("/client/**")
    	        		.filters(f -> f.stripPrefix(1)).uri("http://localhost:9998"))
                .build();
    }
    
	public static void main(String[] args) {
		SpringApplication.run(CmsAppGatewayApplication.class, args);
	}

}
