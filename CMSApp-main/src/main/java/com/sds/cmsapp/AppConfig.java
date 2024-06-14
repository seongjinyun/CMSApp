package com.sds.cmsapp;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sds.cmsapp.jwt.JwtUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

@Configuration
public class AppConfig {

	
	// 로그인 시점보다 먼저 생성
	public JwtUtil jwtUtil() throws Exception {
		return new JwtUtil();
	}
	
	// 어플리케이션 가동 시점부터 공개키와 비밀키 생성
	// 공개키의 경우엔 다른 MSA가 가져갈 수 있도록 API로 제공
	// 이 프로젝트에서는 파일로 공개키를 저장해두지 않고 ServletContext(application 내장객체)에 담아두기
	
	// 아래 객체를 bean으로 등록해두면, application이 동작할 때 감지하여 메서드 실행(자신이 보유한 이벤트 메서드들: onstartup 등)
	@Bean
	public ServletContextInitializer servletContextInitializer(JwtUtil jwtUtil) {
		return new ServletContextInitializer() {
			
			@Override
			public void onStartup(ServletContext servletContext) throws ServletException {
				// TODO Auto-generated method stub
				servletContext.setAttribute("key", jwtUtil.getEncodedPublicKey());
			}
		};
	}
	
	
}
