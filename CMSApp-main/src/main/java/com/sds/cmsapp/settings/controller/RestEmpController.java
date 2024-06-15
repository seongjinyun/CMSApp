package com.sds.cmsapp.settings.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private AuthenticationManager authenticationManager;
    
	@Autowired
	private JwtValidService jwtValidService;
	
	@Autowired
	private EmpDetailService empDetailService;
    	
//    @PostMapping("/emp/login")
//    public ResponseEntity<?> getLoginMember(@RequestHeader("Authorization") String header) {
//        log.debug("RestEmpController 토큰 검증 요청");
////        String token = header.replace("Bearer ", "");
////        Authentication authentication = jwtValidService.getAuthentication(token);
////        SecurityContextHolder.getContext().setAuthentication(authentication);
////        return ResponseEntity.ok(jwtValidService.getEmpFromJwt(token));
//        return null;
//    }
	
	@PostMapping("/emp/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) throws Exception {
	    String empId = loginRequest.get("empId");
	    String empPw = loginRequest.get("empPw");

	    // 인증 로직
	    EmpDetail empDetail = new EmpDetail();
	    empDetail.setEmpId(empId);
	    empDetail.setEmpPw(empPw);
	    
	    Emp emp = empDetailService.selectByLoginData(empDetail);
	    log.debug("empName: "+emp.getEmpName());
	    log.debug("empIdx: "+emp.getEmpIdx());
	    
	    String token;
		token = jwtUtil.generateToken(emp.getEmpIdx(), (long) (10 * 60 * 1000));
		Map<String, Object> response = new HashMap<>();
		response.put("token", token);
		log.debug(token);
//		response.put("empIdx", emp.getEmpIdx());
		return ResponseEntity.ok(response);
	}
}