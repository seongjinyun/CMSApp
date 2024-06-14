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
import com.sds.cmsapp.domain.RequestDocFilterDTO;
import com.sds.cmsapp.domain.ResponseDocCountDTO;
import com.sds.cmsapp.domain.ResponseDocDTO;
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
	ProjectService projectService; // 필터의 프로젝트 목록 조회
	
	@Autowired
	MasterCodeService masterCodeService; // 필터의 결재 상태 목록 조회
	
	/* 결재 진행 상태별 문서 수 조회 (휴지통에 있는 문서 제외) */
	@GetMapping("/dashboard/summary/count")
	public ResponseDocCountDTO getCountByStatus() {
		return documentService.countByStatus();
	}
	
	/* 필터에 따라 결재 진행 중인 문서 목록 조회 (휴지통에 있는 문서 제외) */ //RequestParam 값을 요청 DTO로 자동 매핑
	@GetMapping("/dashboard/list/entire")
	public List<ResponseDocDTO> getFilteredList(RequestDocFilterDTO filterDTO) {
		log.debug("입력받은 상태 코드: " + filterDTO.getStatusList());
		return documentService.selectFilteredList(filterDTO);
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
