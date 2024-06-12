package com.sds.cmsapp.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sds.cmsapp.domain.MasterCode;
import com.sds.cmsapp.domain.Project;
import com.sds.cmsapp.domain.RequestDocumentDTO;
import com.sds.cmsapp.domain.ResponseDocumentCountDTO;
import com.sds.cmsapp.domain.ResponseDocumentDTO;
import com.sds.cmsapp.exception.DocumentException;
import com.sds.cmsapp.exception.FolderException;
import com.sds.cmsapp.exception.StatusLogException;
import com.sds.cmsapp.model.document.DocumentService;
import com.sds.cmsapp.model.mastercode.MasterCodeService;
import com.sds.cmsapp.model.project.ProjectService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController(value="adminDashboardDocumentListController")
public class RestDocumentListController {
	
	@Autowired
	DocumentService documentService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	MasterCodeService masterCodeService;
	
	/* 결재 진행 상태별 문서 수 조회 (휴지통에 있는 문서 제외) */
	@GetMapping("/dashboard/summary/count")
	public ResponseDocumentCountDTO getCountByStatus() {
		log.debug("======== 문서 수 집계 요청이 들어왔습니다. ======== ");
		
		return documentService.countByStatus();
	}
	
	/* 결재 진행 상태별 문서 목록 조회 (휴지통에 있는 문서 제외, 10개까지) */
	@GetMapping("/dashboard/summary/list")
	public List<ResponseDocumentDTO> getSummaryList(@RequestParam(value="statusCode") int statusCode) {
		log.debug("======== 문서 목록 요청이 들어왔습니다. ======== ");
		log.debug("입력받은 상태 코드: " + statusCode);
		
		log.debug("======== 문서 목록 조회를 시작합니다. ======== ");
		List<ResponseDocumentDTO> list = documentService.selectSummaryListOfCurrentStatus(statusCode);
		
		return list;
	}
	
	/* 필터에 따라 결재 진행 중인 문서 목록 조회 (휴지통에 있는 문서 제외) */
	@GetMapping("/dashboard/entire/list")
	public List<ResponseDocumentDTO> getFilteredList(RequestDocumentDTO item) {
		
		log.debug("======== 문서 목록 요청이 들어왔습니다. ======== ");
		log.debug("입력받은 상태 코드: " + item.getStatusCodeList());
		log.debug("입력받은 시작일: " + item.getStartDate());
		log.debug("입력받은 마지막일: " + item.getEndDate());
		log.debug("입력받은 프로젝트 리스트: " + item.getProjectIdxList());
		
		log.debug("======== 문서 목록 조회를 시작합니다. ======== ");
		List<ResponseDocumentDTO> list = documentService.selectFilteredListOfCurrentStatus(item);
		
		return list;
	}
	
	/* 전체 프로젝트 목록 조회 */
	@GetMapping("/admin/project/list")
	public List<Project> getProjectList() {
		return projectService.selectAll();
	}
	
	/* 전체 상태 목록 조회 */
	@GetMapping("/admin/status/list")
	public List<MasterCode> getStatusList() {
		return masterCodeService.selectAll();
	}
	
	@ExceptionHandler({DocumentException.class, FolderException.class, StatusLogException.class})
	public ModelAndView handle(RuntimeException e) {
		ModelAndView mav = new ModelAndView("adminn/error/result");
		mav.addObject("e", e);
		
		return mav;
	}

}
