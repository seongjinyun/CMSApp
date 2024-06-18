package com.sds.cmsapp.model.updating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.MasterCode;
import com.sds.cmsapp.domain.StatusLog;
import com.sds.cmsapp.exception.DocumentVersionException;
import com.sds.cmsapp.exception.StatusLogException;
import com.sds.cmsapp.model.document.DocumentService;
import com.sds.cmsapp.model.document.DocumentVersionService;
import com.sds.cmsapp.model.mastercode.MasterCodeDAO;
import com.sds.cmsapp.model.statuslog.StatusLogService;

@Service
public class MainUpdatingStatusServiceImpl implements MainUpdatingStatusService {

	@Autowired
	DocumentVersionService documentVersionService;

	@Autowired
	StatusLogService statusLogService;

	@Autowired
	DocumentService documentService;
	
	@Autowired
	MasterCodeDAO masterCodeDAO;

	@Transactional
	public void changeStatus(Document document,  Emp emp, MasterCode masterCode, String comments)
			throws DocumentVersionException, StatusLogException {
		
		DocumentVersion documentVersion = new DocumentVersion(document, masterCode);
		StatusLog statusLog = new StatusLog(emp, document, masterCode, comments);

		documentVersionService.changeStatusOne(documentVersion);
		statusLogService.registOne(statusLog);

	}

}
