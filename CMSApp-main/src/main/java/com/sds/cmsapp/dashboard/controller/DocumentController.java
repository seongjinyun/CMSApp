package com.sds.cmsapp.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller(value="dashboardDocumentController")
public class DocumentController {

	@GetMapping("/test/daterange")
	public ResponseEntity getDocumentListByDateRange(
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate
			) {
		
		System.out.println("사용자가 입력한 일자는 " + startDate + " - " + endDate + "입니다.");
		
		ResponseEntity entity = null;		
		
		return entity;
	}
}
