package com.sds.cmsapp.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.cmsapp.domain.Dept;
import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.MasterCode;
import com.sds.cmsapp.domain.Role;
import com.sds.cmsapp.domain.StatusLog;
import com.sds.cmsapp.model.dept.DeptService;
import com.sds.cmsapp.model.document.DocumentService;
import com.sds.cmsapp.model.emp.EmpService;
import com.sds.cmsapp.model.role.RoleService;
import com.sds.cmsapp.model.status.MasterCodeService;
import com.sds.cmsapp.model.status.StatusLogService;

@Controller(value="dashboardDocumentController")
public class DocumentController {

	// 문서
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private EmpService empService;
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private RoleService roleService;
	
	// 상태 로그
	@Autowired
	private StatusLogService statusLogService;
	
	@Autowired
	private MasterCodeService masterCodeService;
	
	
	@GetMapping("/test/daterange")
	public ResponseEntity getDocumentListByDateRange(
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate
			) {
		
		System.out.println("사용자가 입력한 일자는 " + startDate + " - " + endDate + "입니다.");
		
		int doc_idx = 1;
		
		// document 테이블 정보
		Document document = documentService.selectByDocumentIdx(doc_idx);
		
		int doc_hit = document.getHit();
		int doc_empIdx = document.getEmp().getEmp_idx();
		
		System.out.println("이 문서를 생성한 사원의 사원_idx: " + doc_empIdx);
		System.out.println("이 문서의 조회수: " + doc_hit);
		
		// emp 테이블 정보 (문서 생성)
		Emp emp = empService.selectByEmpIdx(doc_empIdx);
		
		String emp_name = emp.getEmp_name();
		int emp_deptIdx = emp.getDept().getDept_idx();
		int emp_roleCode = emp.getRole().getRole_code();
		
		System.out.println("이 문서를 생성한 사원의 이름: " + emp_name);
		System.out.println("이 문서를 생성한 사원의 부서의 부서_idx: " + emp_deptIdx);
		System.out.println("이 문서를 생성한 사원의 부서의 역할의 역할_code: " + emp_roleCode);
		
		// dept 테이블 정보
		Dept dept = deptService.selectByDeptIdx(emp_deptIdx);
		
		String dept_name = dept.getDept_name();
		
		System.out.println("이 문서를 생성한 사원의 부서의 이름: " + dept_name);
		
		// role 테이블 정보
		Role role = roleService.selectByRoleCode(emp_roleCode);
		
		String role_name = role.getRole_name();
				
		System.out.println("이 문서를 생성한 사원의 역할의 이름: " + role_name);
		
		//status_log 테이블 정보
		StatusLog statusLog = statusLogService.selectLatestChangedByDocumentIdx(doc_idx);
			
		int statusLog_idx = statusLog.getStatus_log_idx();
		String statusLog_comments = statusLog.getComments();
		//String statusLog_regDate = statusLog.getRegdate();
		//int statusLog_statusChangerEmpIdx = statusLog.getEmp().getEmp_idx();
		//int statusLog_ChangedStatusCode = statusLog.getMasterCode().getStatus_code();
		
		System.out.println("이 문서의 결재 상태가 가장 마지막에 변경되었을 때의 상태 로그_idx: " + statusLog_idx);
		System.out.println("이 문서의 결재 상태가 가장 마지막에 변경되었을 때의 상태 로그의 코멘트: " + statusLog_comments);
		//System.out.println("이 문서의 결재 상태가 가장 마지막에 변경되었을 때의 상태 로그의 일시: " + statusLog_regDate);
//		
//		// emp 테이블 정보 (상태 변경)
//		Emp statusChangerEmp = empService.selectByEmpIdx(statusLog_statusChangerEmpIdx);
//		
//		String statusChangerEmp_name = statusChangerEmp.getEmp_name();
//		
//		System.out.println("이 문서의 결재 상태가 가장 마지막에 변경되었을 때의 상태 변경자: " + statusChangerEmp_name);
//		
		// mastercode 테이블 정보
		//MasterCode masterCode = masterCodeService.select(statusLog_ChangedStatusCode);
		
		//String changedStatusCode_name = masterCode.getStatus_name();
		
		//System.out.println("이 문서의 결재 상태가 가장 마지막에 변경되었을 때의 상태 코드의 이름: " + changedStatusCode_name);
		
		ResponseEntity entity = null;		
		
		return entity;
	}
}
