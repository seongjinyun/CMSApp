package com.sds.cmsdocument.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sds.cmsdocument.domain.ErrorResponse;
import com.sds.cmsdocument.domain.PublishedVersion;
import com.sds.cmsdocument.domain.RequestPublishingDTO;
import com.sds.cmsdocument.exception.DocumentException;
import com.sds.cmsdocument.exception.DocumentVersionException;
import com.sds.cmsdocument.exception.PublishedVersionException;
import com.sds.cmsdocument.exception.PublishedVersionNameException;
import com.sds.cmsdocument.model.document.DocumentVersionService;
import com.sds.cmsdocument.model.publishing.MainPublishingService;
import com.sds.cmsdocument.model.publishing.PublishedVersionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PublishingController {
	
	@Autowired
	PublishedVersionService publishedVersionService;
	
	@Autowired
	DocumentVersionService documentVersionService;
	
	@Autowired
	MainPublishingService mainPublishingService;
	
	/* 문서 목록 배포 */
	@PostMapping("/admin/dashboard/publishing")
	public ResponseEntity<String> publishDocumentList(RequestPublishingDTO publishingDTO) {
		log.debug("입력받은 배포판 이름: " + publishingDTO.getPublishedVersionName());
		log.debug("입력받은 코멘트: " + publishingDTO.getComments());
		
		mainPublishingService.publishDoc(publishingDTO);
		ResponseEntity<String> entity = ResponseEntity.ok("성공");
		return entity;
	}
	
	// 배포 대기 문서 목록 불러오기
	@GetMapping("/admin/dashboard/publishing/waiting-list")
	public ResponseEntity<?> getWaitingList() {
		
		List<PublishedVersion> waitingList = publishedVersionService.selectWaitingQueue();
		
		ResponseEntity<?> entity = ResponseEntity.ok(waitingList);
		return entity;
	}
	
	@ExceptionHandler({DocumentException.class, DocumentVersionException.class, PublishedVersionNameException.class, PublishedVersionException.class})
	public ResponseEntity<?> handle(RuntimeException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("배포할 문서가 없거나 리뷰가 완료되지 않은 문서가 있습니다."));
	}
}
