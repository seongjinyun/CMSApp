package com.sds.cmsapp.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sds.cmsapp.model.emp.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//@Slf4j
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    
//	@Autowired
//    private JwtUtil jwtUtil;
//	
//	@Autowired
//    private CustomUserDetailsService customUserDetailsService;
//    
////    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
////    	this.jwtUtil = jwtUtil;
////    	this.customUserDetailsService = customUserDetailsService;
////    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//    	
//    	log.warn("JwtAuthenticationFilter 호출");
//
//        final String authorizationHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwt = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7);
//            log.warn("jwt at Filter: "+jwt);
//            username = jwtUtil.extractUsername(jwt);
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
//
//            if (jwtUtil.validateToken(jwt, userDetails)) {
//            	log.warn("token validation: "+jwtUtil.validateToken(jwt, userDetails));
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken
//                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
//        chain.doFilter(request, response);
//    }
//	
//}

@Slf4j
@Component
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter() {
        super(authenticationManager());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(7);
            Claims claims = Jwts.parser()
                    .setSigningKey("secretkey") // 비밀키는 실제 사용 환경에 맞게 설정
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.get("role") != null) {
                // JWT 토큰에서 사용자 정보를 추출하여 인증
                SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(claims));
            }
        } catch (SignatureException e) {
            throw new RuntimeException("JWT Token이 유효하지 않습니다.");
        }

        chain.doFilter(request, response);
    }
}