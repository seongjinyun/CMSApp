package com.sds.cmsdocument.model.document;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsdocument.domain.DocStatus;
import com.sds.cmsdocument.domain.DocumentVersion;
import com.sds.cmsdocument.domain.PublishedVersion;
import com.sds.cmsdocument.domain.VersionLog;
import com.sds.cmsdocument.exception.DocumentVersionException;
import com.sds.cmsdocument.model.publishing.PublishedVersionDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DocumentVersionServiceImpl implements DocumentVersionService {
	
	@Autowired
	private DocumentVersionDAO documentVersionDAO;
	
	@Autowired
	private PublishedVersionDAO publishedVersionDAO;

	//resultMap="DocumentVersionMap"
	public DocumentVersion selectByDocumentIdx(int document_idx) throws DocumentVersionException {
		return documentVersionDAO.selectByDocumentIdx(document_idx);
	}
	
	// [리뷰 요청], [반려]
	@Transactional
	public void changeStatusOne(DocumentVersion documentVersion) throws DocumentVersionException {
		
		int result = documentVersionDAO.updateStatusByDocumentIdx(documentVersion);
		if (result > 0) log.debug("document_version 테이블의 상태 업데이트 성공");
		else throw new DocumentVersionException("document_version 테이블 상태 업데이트 실패");
	}
	
	// [반려 확인] // 문서 버전을 이전 배포판으로 되돌림
	@Transactional
	public void changeStatusRejectedToDraft(DocumentVersion documentVersion) throws DocumentVersionException {
		
		// 이전에 배포된 적 있는 문서라면 버전 정보를 배포된 버전으로 되돌리기
		if (documentVersion.getMasterCode().getStatusCode() == DocStatus.DRAFT.getStatusCode()){
			PublishedVersion latestPublishedVersion = publishedVersionDAO.selectLastestPublishedVersionByDocumentIdx(documentVersion.getDocument().getDocumentIdx());
			if (latestPublishedVersion != null) {
				VersionLog publishedVersionLog = latestPublishedVersion.getVersionLog();
				documentVersion.setVersionLog(publishedVersionLog);
			}
		}
		int result = documentVersionDAO.updateStatusByDocumentIdx(documentVersion);
		if (result > 0) log.debug("document_version 테이블의 상태 업데이트 성공");
		else throw new DocumentVersionException("document_version 테이블 상태 업데이트 실패");
	}


	// [배포하기] 처리 
	@Transactional(propagation = Propagation.REQUIRED)
	public void changeStatusOfPublishedDocList(List<DocumentVersion> publishingWaitingList)
		throws DocumentVersionException{
		
		for (DocumentVersion dv : publishingWaitingList) {
			// 상태 업데이트
			int resultOfUpdatingStatus = documentVersionDAO.updateStatusByDocumentIdxForPublishing(dv);
			if (resultOfUpdatingStatus > 0) log.debug("document_version 테이블의 상태 업데이트 성공");
			else throw new DocumentVersionException("document_version 테이블 상태 업데이트 실패");
		}
	}

}
