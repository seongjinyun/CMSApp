package com.sds.cmssettings.settings.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmssettings.jwt.JwtUtil;
import com.sds.cmssettings.jwt.JwtValidService;
import com.sds.cmssettings.model.emp.EmpDetailService;

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