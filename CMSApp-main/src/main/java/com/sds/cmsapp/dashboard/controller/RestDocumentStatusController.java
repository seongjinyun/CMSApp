package com.sds.cmsapp.dashboard.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.domain.DocStatus;
import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.MasterCode;
import com.sds.cmsapp.domain.RequestDocStatusChanging;
import com.sds.cmsapp.model.mastercode.MasterCodeService;
import com.sds.cmsapp.model.updating.MainUpdatingStatusService;

@RestController(value="dashboardDocumentStatusController")
public class RestDocumentStatusController {
	
	@Autowired
	MasterCodeService masterCodeService;
	
	@Autowired
	MainUpdatingStatusService mainUpdatingStatusService;

	@PostMapping("/admin/document/{documentIdx}/status/in-review")
	public ResponseEntity changeStatusBackToInReview(@PathVariable(value="documentIdx") int documentIdx, RequestDocStatusChanging requestDTO) {
		Emp emp = new Emp(1); // 임시값
		Document document = new Document(documentIdx);
		MasterCode masterCode = masterCodeService.selectByStatusName(DocStatus.IN_REVIEW.getStatusName());
		mainUpdatingStatusService.changeStatus(document, emp, masterCode, requestDTO.getComments());
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/admin/document/{documentIdx}/status/reviewed")
	public ResponseEntity changeStatusToReviewed(@PathVariable(value="documentIdx") int documentIdx, RequestDocStatusChanging requestDTO) {
		Emp emp = new Emp(1); // 임시값
		Document document = new Document(documentIdx);
		MasterCode masterCode = masterCodeService.selectByStatusName(DocStatus.REVIEWED.getStatusName());
		mainUpdatingStatusService.changeStatus(document, emp, masterCode, requestDTO.getComments());
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/admin/document/{documentIdx}/status/rejected")
	public ResponseEntity changeStatusToRejected(@PathVariable(value="documentIdx") int documentIdx, RequestDocStatusChanging requestDTO) {
		Emp emp = new Emp(1); // 임시값
		Document document = new Document(documentIdx);
		MasterCode masterCode = masterCodeService.selectByStatusName(DocStatus.REJECTED.getStatusName());
		mainUpdatingStatusService.changeStatus(document, emp, masterCode, requestDTO.getComments());
		
		return ResponseEntity.ok().build();
	}
	
}
