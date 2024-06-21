package com.sds.cmsdocument.model.updating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsdocument.domain.DocumentVersion;
import com.sds.cmsdocument.exception.DocumentVersionException;
import com.sds.cmsdocument.exception.PublishedVersionException;
import com.sds.cmsdocument.exception.StatusLogException;
import com.sds.cmsdocument.model.document.DocumentService;
import com.sds.cmsdocument.model.document.DocumentVersionService;
import com.sds.cmsdocument.model.statuslog.StatusLogService;

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
