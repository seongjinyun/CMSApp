package com.sds.cmsapp.settings.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.EmpDetail;
import com.sds.cmsapp.jwt.JwtUtil;
import com.sds.cmsapp.jwt.JwtValidService;
import com.sds.cmsapp.model.emp.EmpDetailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestEmpController {

    @Autowired
    private JwtUtil jwtUtil;
    
	@Autowired
	private JwtValidService jwtValidService;
	
	@Autowired
	private EmpDetailService empDetailService;
	
	@PostMapping("/emp/login")
	public String login(@RequestBody Map<String, String> loginRequest) {
		log.debug("로그인 요청 처리 호출");
		return null;
	}
}