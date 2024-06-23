package com.sds.cmsdocument.dashboard.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.sds.cmsdocument.domain.Emp;
import com.sds.cmsdocument.jwt.JwtValidService;

@Controller(value="dashboardDocumentController")
public class DocumentController {
	
	@Autowired
	JwtValidService jwtValidService;

	@GetMapping("/dashboard/activity")
	public String getAdminDashboardActivity() {
		return "dashboard/activity";
	}
	
	@GetMapping("/admin/dashboard/publishing")
	public String getAdminDashboardPublishing() {
		return "dashboard/publishing";
	}
	
	@GetMapping("/emp/check")
	public ResponseEntity<?> getEmpIdx(@RequestHeader(name="Authorization") String header) {
		String token = header.replace("Bearer ", "");
		Emp emp = jwtValidService.getEmpFromJwt(token);
	    String roleName = emp.getRole().getRoleName();
		Map<String, String> response = new HashMap<>();
		response.put("empIdx", ""+emp.getEmpIdx());
		response.put("roleName", roleName);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/checkAuthority/admin/dashboard/publishing")
	public ResponseEntity<?> checkAuthorityForPublishingPage(@RequestHeader(name="Authorization") String header) {
	    String token = header.replace("Bearer ", "");
	    Emp emp = jwtValidService.getEmpFromJwt(token);
	    String roleName = emp.getRole().getRoleName();
	    
	    Map<String, String> response = new HashMap<>();
	    if(roleName.equals("Admin")) {
	        return ResponseEntity.ok(response);
	    } else {
	        response.put("url", "/error");
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	    }
	}
}
