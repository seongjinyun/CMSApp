package com.sds.cmsapp.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.cmsapp.domain.Dept;
import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.MasterCode;
import com.sds.cmsapp.domain.Role;
import com.sds.cmsapp.domain.StatusLog;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.model.document.DocumentVersionService;

@Controller
public class TestController {

	// 문서-(결재 진행 중인)버전 테이블
	@Autowired
	private DocumentVersionService documentVersionService;
	
	@GetMapping("/test/query")
	public ResponseEntity testQuery(
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate
			) {
		
		System.out.println("사용자가 입력한 일자는 " + startDate + " - " + endDate + "입니다.");
		
		DocumentVersion dv = documentVersionService.selectByDocumentIdx(1);
		
		// 1. version_log
		VersionLog versionLog = dv.getVersionLog();
		String title = versionLog.getTitle();
		String comments = versionLog.getComments();
		System.out.println("제목: " + versionLog.getTitle());
		System.out.println("버전 변경 코멘트: " + comments);
		
		// 2. document
		Document d = dv.getDocument();
		
		// 2-1. document > status_log
		StatusLog statusLog = d.getLatestRegisteredStatusLog();
		String statusLog_comments = statusLog.getComments();
		String statusLog_regdate = statusLog.getRegdate();
		System.out.println(statusLog_comments);
		System.out.println(statusLog_regdate);
		
		// 2-1-1. document > status_log > mastercode
		MasterCode masterCode = statusLog.getMasterCode();
		String status_name = masterCode.getStatus_name();
		System.out.println(status_name);
		
		// 2-1-2. document > status_log > emp
		Emp statusChangerEmp = statusLog.getEmp();
		String statusChangerEmp_name = statusChangerEmp.getEmp_name();
		System.out.println(statusChangerEmp_name);
	
		// 2-1-2-1. document > status_log > emp > role
		Role statuscCangerEmp_role = statusChangerEmp.getRole();
		String statusChangerEmp_roleName = statuscCangerEmp_role.getRole_name();
		System.out.println(statusChangerEmp_roleName);
		
		// 2-1-2-2. document > status_log > emp > dept
		Dept statusChangerEmp_dept = statusChangerEmp.getDept();
		String statusChangerEmp_deptName = statusChangerEmp_dept.getDept_name();
		System.out.println(statusChangerEmp_deptName);

		ResponseEntity entity = null;		
		
		return entity;
	}
}
