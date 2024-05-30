package com.sds.cmsapp.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value="DashboardDocumentController")
public class DocumentController {
	
	@GetMapping("test/daterange")
	public ResponseEntity getDocumentList() {
		
		System.out.println("달력 선택");
		
		ResponseEntity entity = null;
		return entity;
	}

}
