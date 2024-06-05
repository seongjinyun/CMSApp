package com.sds.cmsapp.dashboard.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.domain.DashboardDocument;
import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.model.document.DocumentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController(value = "dashboardRestDocumentListcontroller")
public class RestDocumentListController {

	@Autowired
	private DocumentService documentService;

	/*-------------------------------------
	 * [요약] 탭 - 결재 상태별 문서 수 집계
	 -------------------------------------*/
	@GetMapping("/dashboard/list/count")
	public int getSummaryCount(@RequestParam(value = "status_code", required = false) int status_code) {
		return documentService.countForDashboard(status_code);
	}

	/*-------------------------------------
	 *  [요약], [전체보기] 탭 - 목록
	-------------------------------------*/
	@GetMapping("/dashboard/list")
	public List<DashboardDocument> getSummaryList(
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "status_code", required = false) String status_code
	// ,
	// @RequestParam(value="emp_idx", required=false) int emp_idx,
	// @RequestParam(value="dept_idx", required=false) int dept_idx
	) {
		
		// 모델에 보낼 map 만들기
		Map map = new HashMap();

		// 상태 코드
		map.put("status_code", status_code);
		
		// 날짜
		if(StringUtils.hasText(startDate)) {
			// 날짜 파라미터가 있을 경우 (전체 보기 탭) - String을 Timestamp로 변환
			Timestamp start_date = Timestamp.valueOf(startDate);
			Timestamp end_date = Timestamp.valueOf(endDate);
			map.put("start_date", start_date);
			map.put("end_date", end_date);
		} else {
			// 날짜 파라미터가 없을 경우 (요약 탭)
			map.put("start_date", null);
			map.put("end_date", null); 
		}
		
		// map.put("emp_idx", emp_idx);
		// map.put("dept_idx", dept_idx);

		// 모델 일 시키기
		List<Document> documentList = documentService.selectAllForDashboard(map);
		System.out.println("조회된 문서 수: " + documentList.size());

		// 브라우저에 보낼 정보를 DashboardDocument DTO에 
		List<DashboardDocument> dashboardDocumentList = new ArrayList();

		for (Document d : documentList) {
			
			// Timestamp를 String으로 변환
			Date date = new Date();
			date.setTime(d.getLatestRegisteredStatusLog().getRegdate().getTime());
			String regdate = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(date);
			// 이것도 쓸 수 있음. DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			log.debug("문서 번호: " + d.getDocument_idx());
			log.debug("상태 변경일: " + regdate);
			log.debug("현 상태: " + d.getLatestRegisteredStatusLog().getMasterCode().getStatus_name());
			
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
