package com.sds.cmsapp.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.cmsapp.domain.CustomUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

// 지금까지 스프링 시큐리티에서 자동으로 설정된 UsernamePasswordAuthenticationFilter를 이용하여 전통적인 세션방식의 로그인을 처리했지만
// 이 시점부터는 세션 방식의 로그인을 이용하지 않기 위해 UsernamePasswordAuthenticationFilter 를 커스텀
// 이 클래스를 재정의 한다는 것 자체가 jwt를 이용하는것은 아니다. 단지 이 클래스는 로그인 하는 순간을 제어할 수 있는 클래스이다
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter{

	private JwtUtil jwtUtil;
	
	// AuthenticationManager 는 인터페이스이고, 스프링시큐리티 내부적으로 생성되어 처리되므로 개발자는 스프링으로부터 얻어와 사용해야 한다..
	private AuthenticationManager authenticationManager;

//	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public LoginFilter(AuthenticationManager authenticationManager) throws Exception{//, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.authenticationManager = authenticationManager; 
//		this.bCryptPasswordEncoder = bCryptPasswordEncoder; // 암호화된 비밀번호 확인을 위함		
		jwtUtil = new JwtUtil();
	}
	
	// 사용자가 로그인 시도시
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		// 사용자가 로그인폼에서 전송한 아이디,패스워드이 이 메서드로 전달되는지 부터 체크 
		String empId = this.obtainUsername(request);
		String empPw = this.obtainPassword(request);

		log.debug("empId is ======================"+empId);
		log.debug("empPassword is ======================"+empPw);
		
		// 사원ID과 비밀번호를 UsernamePasswordAthenticationToken 객체에 담아놓고 
		// (스프링 시큐리티로 하여금) db를 연동하여 회원정보를 인증하는 절차 진행
		// CustomerUserDetailsService 객체를 이용하여 db를 조회해주는 객체가 AuthenticationManager
		Authentication auth = new UsernamePasswordAuthenticationToken(empId, empPw, null);
		// UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(empId, empPassword);
		
		return authenticationManager.authenticate(auth); // CustomUserDetailsService의 db조회 메서드 호출!!
	}
	
	// 스프링 시큐리티가 무조건 username 파라미터를 찾으므로 만일 개발자가 다른 이름의 파라미터를 이용하고 있다면
	// object~ 메서드를 재정의하면 된다, (여기서는 getParameter를 id로 설정해 획득)
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter("empId");
	}
	
    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter("empPw");
    }
	
    // On successful authentication, create JWT token with empName and role
	// 사용자가 인증 성공시
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		
		CustomUserDetails customUserDetails=(CustomUserDetails)authentication.getPrincipal();
		
		String empName = customUserDetails.getEmp().getEmpName();
		int empIdx     = customUserDetails.getEmp().getEmpIdx();
		String role    = customUserDetails.getEmp().getRole().getRoleName();
		int roleCode   = customUserDetails.getEmp().getRole().getRoleCode();
		
		log.debug("사원정보가 존재합니다. 로그인 성공");
		
		// 회원정보가 존재하므로, 세션 로그인 처리 대신 클라이언트에게 JWT 토큰을 발급해주자 
		long expireTime = (1*1000*60)*1; // 1분
		String token = null;
		try {
			token = jwtUtil.generateToken(empName, empIdx, role, expireTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 생성된 키 중에서 공개키를 ServletContext에 담아두고, MSA를 이루는 서비스들이 요구할때 api로 제공
		
		
		// jsp에서의 application 내장객체
		request.getServletContext().setAttribute("key", jwtUtil.getEncodedPublicKey()); // 인코딩된 문자열
		log.debug("ServletContextdp 담게될 인코딩된 publicKey is "+jwtUtil.getEncodedPublicKey());
		
		
		// 생성된 결과물인 토큰을 클라이언트에게 전송: 응답정보를 생성
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("success", true);
		responseMap.put("emp", customUserDetails.getEmp());
		responseMap.put("token", token);
		
		response.setStatus(HttpServletResponse.SC_OK); // 200
		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
	}
	
	//사용자 인증이 실패되면..
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		log.debug("회원정보가 없습니다.로그인 실패");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
		response.setContentType("application/json");
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("success", false);
		responseMap.put("message", "Authentication failed");
		new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
	}
}