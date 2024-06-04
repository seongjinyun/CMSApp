package com.sds.cmsapp.dashboard.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.domain.DashboardDocument;
import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.model.document.DocumentService;


@RestController(value="DashboardDocumentController")
public class DocumentController {
	
	// 문서-(결재 진행 중인)버전 테이블
	@Autowired
	private DocumentService documentService;
	
	/*************************
	 *  [요약] 탭의 목록
	 *************************/
	@GetMapping("/dashboard/summary/list")
	public List<DashboardDocument> getSummaryList(
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate,
			@RequestParam(value="status_code", required=false) String status_code
			//,
			//@RequestParam(value="emp_idx", required=false) int emp_idx,
			//@RequestParam(value="dept_idx", required=false) int dept_idx
			) {
		
		//System.out.println("사용자가 입력한 일자는 " + startDate + " - " + endDate + "입니다.");
		
		
		//Timestamp start_date = Timestamp.valueOf(startDate);
		//Timestamp end_date = Timestamp.valueOf(endDate);
		
		//System.out.println(start_date);
		//System.out.println(end_date);
		
		
		// 모델 일 시키기
		Map map = new HashMap();
		map.put("start_date", null);
		map.put("end_date", null);
		map.put("status_code", status_code);
		//map.put("emp_idx", emp_idx);
		//map.put("dept_idx", dept_idx);
		
		List<Document> documentList  = documentService.selectAllForDashboard(map);
		System.out.println("조회된 문서 수: " + documentList.size());
		
		// 결과 저장하기
		List<DashboardDocument> dashboardDocumentList = new ArrayList();
		
		for (Document d : documentList) {
			System.out.println("document_idx: " + d.getDocument_idx());
			//System.out.println("document_version_idx: " + d.getDocumentVersion().getDocument_version_idx());
			System.out.println("version: " + d.getDocumentVersion().getVersionLog().getVersion());
			System.out.println("title: " + d.getDocumentVersion().getVersionLog().getTitle());
			
			
			System.out.println("상태 변경 코멘트: " + d.getLatestRegisteredStatusLog().getComments());
			
			Date date = new Date();
			date.setTime(d.getLatestRegisteredStatusLog().getRegdate().getTime());
			String regdate = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(date);
			
			System.out.println("상태 변경일: " + regdate);
			
			//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			
			//System.out.println(d.getLatestRegisteredStatusLog().getEmp().getEmp_idx());
			System.out.println("상태 변경자: " + d.getLatestRegisteredStatusLog().getEmp().getEmp_name());
			
			System.out.println("상태 변경자 소속 부서: " + d.getLatestRegisteredStatusLog().getEmp().getDept().getDept_name());
			System.out.println("상태 변경자 역할명: " + d.getLatestRegisteredStatusLog().getEmp().getRole().getRole_name());
			
			System.out.println("현 상태: " + d.getLatestRegisteredStatusLog().getMasterCode().getStatus_name());
			
			System.out.println("--");
			
			DashboardDocument dd = new DashboardDocument();
			dd.setDocument_idx(d.getDocument_idx());
			dd.setVersion(d.getDocumentVersion().getVersionLog().getVersion());
			dd.setTitle(d.getDocumentVersion().getVersionLog().getTitle());
			dd.setComments(d.getLatestRegisteredStatusLog().getComments());
			dd.setEmp_name(d.getLatestRegisteredStatusLog().getEmp().getEmp_name());
			dd.setDept_name(d.getLatestRegisteredStatusLog().getEmp().getDept().getDept_name());
			dd.setRole_name(d.getLatestRegisteredStatusLog().getEmp().getRole().getRole_name());
			dd.setStatus_name(d.getLatestRegisteredStatusLog().getMasterCode().getStatus_name());
			dd.setRegdate(regdate);
			
			dashboardDocumentList.add(dd);
		}
		
		//String array = new Gson().toJson(dashboardDocumentList);
		
		
		return dashboardDocumentList;
	}

	/*************************
	 *  [전체 보기] 탭의 목록
	 *************************/
	@GetMapping("/dashboard/entire/list")
	public List<DashboardDocument> getFilteredList(
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate,
			@RequestParam(value="status_code", required=false) String status_code
			//,
			//@RequestParam(value="emp_idx", required=false) int emp_idx,
			//@RequestParam(value="dept_idx", required=false) int dept_idx
			) {
		
		System.out.println("사용자가 입력한 일자는 " + startDate + " - " + endDate + "입니다.");
				
		Timestamp start_date;
		try {
			start_date = Timestamp.valueOf(startDate);
			System.out.println(start_date);
		} catch (Exception e) {
			//e.printStackTrace();
			start_date = null;
		}
		
		Timestamp end_date = Timestamp.valueOf(endDate);
		
		System.out.println(end_date);
		System.out.println(status_code);
		
		// 모델 일 시키기
		Map map = new HashMap();
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		map.put("status_code", status_code);
		//map.put("emp_idx", emp_idx);
		//map.put("dept_idx", dept_idx);
		
		List<Document> documentList  = documentService.selectAllForDashboard(map);
		System.out.println("조회된 문서 수: " + documentList.size());
		
		// 결과 저장하기
		List<DashboardDocument> dashboardDocumentList = new ArrayList();
		
		for (Document d : documentList) {
			System.out.println("document_idx: " + d.getDocument_idx());
			System.out.println("version: " + d.getDocumentVersion().getVersionLog().getVersion());
			System.out.println("title: " + d.getDocumentVersion().getVersionLog().getTitle());
			
			System.out.println("상태 변경 코멘트: " + d.getLatestRegisteredStatusLog().getComments());
			
			// 데이터베이스의 Timestamp를 string으로 변환
			Date date = new Date();
			date.setTime(d.getLatestRegisteredStatusLog().getRegdate().getTime());
			String regdate = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(date);
			System.out.println("상태 변경일: " + regdate);
			
			//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			
			//System.out.println(d.getLatestRegisteredStatusLog().getEmp().getEmp_idx());
			System.out.println("상태 변경자: " + d.getLatestRegisteredStatusLog().getEmp().getEmp_name());
			
			System.out.println("상태 변경자 소속 부서: " + d.getLatestRegisteredStatusLog().getEmp().getDept().getDept_name());
			System.out.println("상태 변경자 역할명: " + d.getLatestRegisteredStatusLog().getEmp().getRole().getRole_name());
			
			System.out.println("현 상태: " + d.getLatestRegisteredStatusLog().getMasterCode().getStatus_name());
			
			System.out.println("--");
			
			DashboardDocument dd = new DashboardDocument();
			dd.setDocument_idx(d.getDocument_idx());
			dd.setVersion(d.getDocumentVersion().getVersionLog().getVersion());
			dd.setTitle(d.getDocumentVersion().getVersionLog().getTitle());
			dd.setComments(d.getLatestRegisteredStatusLog().getComments());
			dd.setEmp_name(d.getLatestRegisteredStatusLog().getEmp().getEmp_name());
			dd.setDept_name(d.getLatestRegisteredStatusLog().getEmp().getDept().getDept_name());
			dd.setRole_name(d.getLatestRegisteredStatusLog().getEmp().getRole().getRole_name());
			dd.setStatus_name(d.getLatestRegisteredStatusLog().getMasterCode().getStatus_name());
			dd.setRegdate(regdate);
			
			dashboardDocumentList.add(dd);
		}
		
		return dashboardDocumentList;
	}
	
}
