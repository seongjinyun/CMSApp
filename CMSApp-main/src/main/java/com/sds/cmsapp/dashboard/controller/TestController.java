package com.sds.cmsapp.dashboard.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.model.document.DocumentService;

@Controller
public class TestController {

	// 문서-(결재 진행 중인)버전 테이블
	@Autowired
	private DocumentService documentService;
	
	@GetMapping("/test/query")
	public ResponseEntity testQuery(
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate
			) {
		
		System.out.println("사용자가 입력한 일자는 " + startDate + " - " + endDate + "입니다.");
		

		Map map = new HashMap();
		map.put("status_code", 200);

		List<Document> documentList  = documentService.selectAllForDashboard(map);
		
		System.out.println(documentList.size());
		
		for (Document d : documentList) {
			System.out.println(d.getDocument_idx());
			System.out.println(d.getDocumentVersion().getDocument_version_idx());
			System.out.println(d.getDocumentVersion().getVersionLog().getVersion_log_idx());
			System.out.println(d.getDocumentVersion().getVersionLog().getTitle());
			
			
			System.out.println(d.getLatestRegisteredStatusLog().getComments());
			System.out.println(d.getLatestRegisteredStatusLog().getRegdate());
			
			System.out.println(d.getLatestRegisteredStatusLog().getEmp().getEmp_idx());
			System.out.println(d.getLatestRegisteredStatusLog().getEmp().getEmp_name());
			
//			System.out.println(d.getLatestRegisteredStatusLog().getEmp().getDept().getDept_name());
//			System.out.println(d.getLatestRegisteredStatusLog().getEmp().getRole().getRole_name());
//			
			System.out.println(d.getLatestRegisteredStatusLog().getMasterCode().getStatus_name());
			
			System.out.println("--");
		}

		
		

		ResponseEntity entity = null;		
		
		return entity;
	}
}
