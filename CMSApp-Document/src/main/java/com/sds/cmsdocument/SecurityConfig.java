package com.sds.cmsdocument;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//public class SecurityConfig {
//	
//    @Bean
//    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//        .authorizeHttpRequests(
//        		(auth) -> auth
//        		.anyRequest().permitAll() // 모든 요청에 대해 접근을 허용
//        ); 
//        
//        // CSRF 보호 비활성화 (필요한 경우 활성화)
//        http.csrf((auth->auth.disable()));
//    
//		return http.build();
//    }
//    
//}