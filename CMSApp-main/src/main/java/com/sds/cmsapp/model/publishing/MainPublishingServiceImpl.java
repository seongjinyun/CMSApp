package com.sds.cmsapp.model.publishing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.PublishedVersion;
import com.sds.cmsapp.domain.PublishedVersionName;
import com.sds.cmsapp.domain.RequestPublishingDTO;
import com.sds.cmsapp.exception.DocumentVersionException;
import com.sds.cmsapp.exception.PublishedVersionException;
import com.sds.cmsapp.exception.PublishedVersionNameException;
import com.sds.cmsapp.exception.StatusLogException;
import com.sds.cmsapp.model.document.DocumentVersionService;
import com.sds.cmsapp.model.statuslog.StatusLogService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MainPublishingServiceImpl implements MainPublishingService {
	
	@Autowired
	PublishedVersionService publishedVersionService;
	
	@Autowired
	DocumentVersionService documentVersionService;
	
	@Autowired
	StatusLogService statusLogService;

	@Transactional
	public void publishDoc(RequestPublishingDTO publishingDTO)
		throws PublishedVersionNameException, PublishedVersionException, DocumentVersionException, StatusLogException {
		// published_version_name 테이블에 배포판 이름 추가
		PublishedVersionName publishedVerName = new PublishedVersionName(publishingDTO.getPublishedVersionName());
		PublishedVersionName newPublishedVerName = publishedVersionService.registPublishedVersionName(publishedVerName);
		// 배포 대기 목록을 PublishedVersion 리스트로 가져오기 (위에서 select key를 가져옴)
		List<PublishedVersion> publishedVerList = publishedVersionService.selectWaitingQueue(newPublishedVerName);
		log.debug("배포 대기 목록 조회 완료. 배포 예정 문서 수: " + publishedVerList.size());
		// published_version 테이블에 배포 대기 목록 배포하기
		publishedVersionService.registPublishedVersion(publishedVerList);
		
		// document_version 테이블에서 배포한 문서의 상태 변경하기
		documentVersionService.changeStatusOfPublishedDocList(publishedVerList);
		
		// status_log 테이블에 배포한 문서 로그 쌓기
		// 사원 세션 정보 불러오고 나면 사원 정보도 추가하기. 지금은 임시로 1번 사원 넣어놓았음
		statusLogService.registPublishedLog(publishedVerList, publishingDTO.getComments(), new Emp(1));
		
	}
}
