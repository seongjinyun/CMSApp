package com.sds.cmsapp.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.cmsapp.domain.PublishedVersion;
import com.sds.cmsapp.domain.PublishedVersionName;
import com.sds.cmsapp.domain.RequestPublishingDTO;
import com.sds.cmsapp.exception.DocumentException;
import com.sds.cmsapp.exception.DocumentVersionException;
import com.sds.cmsapp.exception.PublishedVersionException;
import com.sds.cmsapp.exception.PublishedVersionNameException;
import com.sds.cmsapp.model.document.DocumentVersionService;
import com.sds.cmsapp.model.publishing.PublishedVersionService;
import com.sds.cmsapp.model.statuslog.StatusLogService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PublishingController {
	
	@Autowired
	PublishedVersionService publishedVersionService;
	
	@Autowired
	DocumentVersionService documentVersionService;
	
	@Autowired
	StatusLogService statusLogService;
	
	/////////두 서비스의 트랜잭션 처리가 필요함
	
	/// 배포된 버전인지 확인하려면 published_version과 조인해야 함
	// 현재 status_log와 version_log_idx와의 관계가 애매함
	
	/* 문서 목록 배포 */
	@PostMapping("/admin/dashboard/publish/new")
	public ResponseEntity<String> publishDocumentList(RequestPublishingDTO publishingDTO) {
		log.debug("입력받은 배포판 이름: " + publishingDTO.getPublishedVersionName());
		PublishedVersionName publishedVerName = PublishedVersionName.builder()
				.publishedVersionName(publishingDTO.getPublishedVersionName()).build();
		
		// published_version_name 테이블에 배포판 이름 추가
		PublishedVersionName newPublishedVerName = publishedVersionService.registPublishedVersionName(publishedVerName);
		
		// 배포 대기 목록을 PublishedVersion 리스트로 가져오기 (위에서 select key를 가져옴)
		List<PublishedVersion> publishedVerList = publishedVersionService.selectWaitingQueue(newPublishedVerName);
		log.debug("배포 대기 목록 조회 완료. 배포 예정 문서 수: " + publishedVerList.size());
		
		// published_version 테이블에 배포 대기 목록 배포하기
		publishedVersionService.registPublishedVersion(publishedVerList);
		
		// document_version 테이블에서 배포한 문서의 상태 변경하기
		documentVersionService.changeStatusOfPublishedDoc(publishedVerList);

		// status_log 테이블에 배포한 문서 로그 쌓기
		// 사원 세션 정보 불러오고 나면 사원 정보도 추가하기
		log.debug("입력받은 코멘트: " + publishingDTO.getComments());
		statusLogService.registPublishedLog(publishedVerList, publishingDTO.getComments());

		return ResponseEntity.ok().build();
	}
	
	@ExceptionHandler({DocumentException.class, DocumentVersionException.class, PublishedVersionNameException.class, PublishedVersionException.class})
	public ModelAndView handle(RuntimeException e) {
		ModelAndView mav = new ModelAndView("admin/error/result");
		mav.addObject("e", e);
		return mav;
	}
}
