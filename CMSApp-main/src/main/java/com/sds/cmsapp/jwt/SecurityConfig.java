package com.sds.cmsapp.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//스프링 3.0 부터 스프링빈 xml 파일을 대신하여 어노테이션 기반으로 설정할 수 있도록 지원..
/*
 <bean id="msg" class="SecurityFilterChain">
 	<constructor-args value="바보"/>
 </bean> 
*/ 

@Configuration
public class SecurityConfig {
	
	//스프링이 지원하는 단방향 암호화(해시) 객체 등록
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//AuthenticationManager 는 직접 new 할 수 없기 때문에, AuthenticationConfiguration 객체를 
	//통해 인스턴스를 얻어와야 한다, SpringSecurity 5.5 쯤부터 AuthenticationConfiguration는 
	//autowired가 지원됨
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	
	@Bean
	public AuthenticationManager authenticationManager() throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
	//우리가 spring mvc 에서 AOP를 이용하여 uri를 걸러낸 작업을 스프링 부트 시큐리티에서는 보다 쉽고 체계적으로
	//지원...
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		
		//필터링 할 uri 명시..
		httpSecurity
			.authorizeHttpRequests(
				(auth) -> auth
				
				/*--------------------------------------------------
				 인증 불필요
				--------------------------------------------------*/
				 .anyRequest().permitAll() // 모든 요청에 대해 접근을 허용

				/*--------------------------------------------------
				 인증 필요
				--------------------------------------------------*/ 
//				.anyRequest().authenticated()
			);
		
		//개발자가 정의한 필터가 적용되도록 등록 
		//이때는 기존 세션 방식의 로그인 방식을 폼로그인 방식이라 하며, 이 기능을 비활성화 시켜야함 
		httpSecurity.formLogin((auth)-> auth.disable());
		
		//기존의 UsernamePasswordAuthenticationFilter 를 대시한 객체를 명시
		LoginFilter customFilter = new LoginFilter(authenticationManager());
//		customFilter.setFilterProcessesUrl("/rest/emp/login");
		httpSecurity.addFilterAt(customFilter, UsernamePasswordAuthenticationFilter.class);

		//로그인에 대한 설정
		//httpSecurity
			//.formLogin((auth)->
				//auth.loginPage("/member/loginform")
				//.successHandler(loginEventHandler()) //개발자가 정의한 핸들러 등록...
				//auth.loginProcessingUrl("/member/login")
				//.usernameParameter("uid")/
				//.passwordParameter("password")
			//);
		
		//로그아웃 설정 
		/*
		httpSecurity
		.logout(logout -> logout
			.logoutUrl("/member/logout") //로그아웃 요청 페이지경로
			.logoutSuccessUrl("/") //로그아웃 처리 후, 보여질 링크 
			.invalidateHttpSession(true) //세션 무효화 
			.clearAuthentication(true) //권한 없애기 
			.deleteCookies("JSESSIONID")
		);
		*/
		
		//토큰 비활성화 
		httpSecurity.csrf((auth)->auth.disable());
		
		return httpSecurity.build();
	}

	/*-----------------------------------------------------------
	  OAuth 유져와  시큐리티를 이용한 홈페이지 로그인 유저가 session에 공통의 member DTO를
	  갖고 있게 하면, header 뿐 아니라, 회원 정보를 세션에서 꺼내올때 member로 통일 할 수 있다.. 
	  이를 위해, 시큐리티한테 모든걸 맡기지 말고, 로그인 하는 시점을 낚아 채서, 억지로 session 에 
	  member DTO를 심어버리자..
	-----------------------------------------------------------*/
	/* 
	public AuthenticationSuccessHandler loginEventHandler() {
		return new LoginEventHandler();
	}
	*/
}