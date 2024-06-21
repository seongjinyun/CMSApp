package com.sds.cmsapp.model.updating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.exception.DocumentVersionException;
import com.sds.cmsapp.exception.PublishedVersionException;
import com.sds.cmsapp.exception.StatusLogException;
import com.sds.cmsapp.model.document.DocumentService;
import com.sds.cmsapp.model.document.DocumentVersionService;
import com.sds.cmsapp.model.statuslog.StatusLogService;
import com.sds.cmsapp.model.versionlog.VersionLogDAO;

@Service
public class MainUpdatingStatusServiceImpl implements MainUpdatingStatusService {

	@Autowired
	DocumentVersionService documentVersionService;

	@Autowired
	StatusLogService statusLogService;

	@Autowired
	DocumentService documentService;
	
	@Transactional
	public void changeStatusOne(DocumentVersion documentVersion) throws DocumentVersionException, StatusLogException {
		
		// documentVersion 상태 업데이트
		documentVersionService.changeStatusOne(documentVersion);
		
		// status_log 추가
		statusLogService.insertByDocumentVersion(documentVersion);
	}
	
	@Transactional 
	public void changeStatusRejectedToDraft(DocumentVersion documentVersion) throws DocumentVersionException, StatusLogException, PublishedVersionException {
		
		// documentVersion 상태 업데이트
		documentVersionService.changeStatusRejectedToDraft(documentVersion);
		
		// status_log 추가
		statusLogService.insertByDocumentVersion(documentVersion);
		
	}

}
