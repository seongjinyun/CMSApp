package com.sds.cmsapp;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
	/*
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
        .anyRequest().permitAll() // 모든 요청에 대해 접근을 허용
        .and()
        .csrf().disable(); // CSRF 보호 비활성화 (필요한 경우 활성화)
    
		return http.build();
    }
    */
}
