package com.sds.cmsapp.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.model.document.DocumentService;

@Controller(value="dashboardDocumentController")
public class DocumentController {

	@Autowired
	private DocumentService documentService;
	
	@GetMapping("/test/daterange")
	public ResponseEntity getDocumentListByDateRange(
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate
			) {
		
		System.out.println("사용자가 입력한 일자는 " + startDate + " - " + endDate + "입니다.");
		
		Document document = documentService.select(1);
		
		System.out.println("이 문서의 조회수: " + document.getHit());
		
		ResponseEntity entity = null;		
		
		return entity;
	}
}
