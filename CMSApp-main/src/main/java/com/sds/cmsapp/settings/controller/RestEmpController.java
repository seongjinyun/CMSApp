package com.sds.cmsapp.settings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.jwt.JwtUtil;
import com.sds.cmsapp.jwt.JwtValidService;
import com.sds.cmsapp.model.emp.EmpDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestEmpController {

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmpDAO empDAO;
	
	@Autowired
	private JwtValidService jwtValidService;
	
//	@PostMapping("/emp/login")
//    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) throws Exception {
//        try {
//            String empId = credentials.get("empId");
//            String empPw = credentials.get("empPw");
//
//            log.debug("empId: "+empId);
//            log.debug("empPw: "+empPw);
//            
//            Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(empId, empPw)
//            );
//
//            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//            String token = jwtUtil.generateToken(userDetails.getEmp().getEmpName(),
//                                                 userDetails.getEmp().getEmpIdx(),
//                                                 userDetails.getEmp().getRole().getRoleName(),
//                                                 1000L); // 1 hour expiration time
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("token", token);
//            response.put("emp", userDetails.getEmp());
//
//            return ResponseEntity.ok(response);
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(401).body(null);
//        }
//    }
	
	// 사용자 인증 API
	// 넘겨받은 토큰이 유효한지 여부를 따져보고, 유효할 경우엔  Emp를 json으로 응답 
	@PostMapping("/rest/emp/logincheck")
	public ResponseEntity getLoginMember(@RequestHeader("Authorization") String header) {
		log.debug("RestEmpController 토큰 검증 요청");
		// 넘어온 헤더값 중, Bearer를 제외한 순수 토큰만 추출 
		return ResponseEntity.ok(jwtValidService.getEmpFromJwt(header.replace("Bearer", "")));
	}	
}
