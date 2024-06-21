package com.sds.cmssetting.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.cmssetting.domain.CustomUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private JwtUtil jwtUtil;
    
    private AuthenticationManager authenticationManager;

    public LoginFilter(JwtUtil jwtUtil, AuthenticationManager authenticationManager ) {
    	super.setAuthenticationManager(authenticationManager);
    	this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/emp/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String empId = null;
        String empPw = null;
        try {
            // Read JSON from request body
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> loginRequest = objectMapper.readValue(request.getInputStream(), Map.class);
            empId = loginRequest.get("empId");
            empPw = loginRequest.get("empPw");
        } catch (Exception e) {
            log.error("Failed to parse authentication request body", e);
        }
        log.debug("empId is ======================" + empId);
        log.debug("empPw is ======================" + empPw);
        if (empId == null || empPw == null) {
            throw new BadCredentialsException("Missing empId or empPw in request");
        }
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(empId, empPw);
        
        try {
            Authentication auth = authenticationManager.authenticate(authToken);
            log.warn("auth is " + auth);
            return auth;
        } catch (AuthenticationException e) {
        	e.printStackTrace();
            log.debug("Authentication failed: " + e.getMessage());
            throw e;
        }
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
    	return request.getParameter("empId");
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter("empPw");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

    	CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    	log.debug("customUserDetails: "+customUserDetails);
        int empIdx = customUserDetails.getEmp().getEmpIdx();
        String empId = customUserDetails.getEmpDetail().getEmpId();
        Collection<? extends GrantedAuthority> role = customUserDetails.getAuthorities();

        log.warn("사원정보가 존재합니다. 로그인 성공");

        long expireTime = (1 * 1000 * 60) * 10; // 10분
        String token = null;
        try {
            token = jwtUtil.generateToken(empId, empIdx, expireTime, role);
        } catch (Exception e) {
        	log.error("JWT 생성 중 오류 발생", e);
            e.printStackTrace();
        }

        request.getServletContext().setAttribute("key", jwtUtil.getEncodedPublicKey());
        log.debug("ServletContext에 담게될 인코딩된 publicKey is " + jwtUtil.getEncodedPublicKey());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", true);
        responseMap.put("token", token);

        response.setStatus(HttpServletResponse.SC_OK); // 200
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.debug("회원정보가 없습니다. 로그인 실패");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setContentType("application/json");
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", false);
        responseMap.put("message", "Authentication failed");
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }
}
