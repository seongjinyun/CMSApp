package com.sds.cmsdocument.model.updating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsdocument.domain.Document;
import com.sds.cmsdocument.domain.DocumentVersion;
import com.sds.cmsdocument.domain.Emp;
import com.sds.cmsdocument.domain.MasterCode;
import com.sds.cmsdocument.exception.DocumentVersionException;
import com.sds.cmsdocument.exception.StatusLogException;
import com.sds.cmsdocument.model.document.DocumentService;
import com.sds.cmsdocument.model.document.DocumentVersionService;
import com.sds.cmsdocument.model.mastercode.MasterCodeDAO;

@Service
public class MainUpdatingStatusServiceImpl implements MainUpdatingStatusService {

	@Autowired
	DocumentVersionService documentVersionService;


	@Autowired
	DocumentService documentService;
	
	@Autowired
	MasterCodeDAO masterCodeDAO;

	@Transactional
	public void changeStatus(Document document,  Emp emp, MasterCode masterCode, String comments)
			throws DocumentVersionException, StatusLogException {
//		if(masterCode.getStatusCode() == 200) {
//			
//		}else if(masterCode.getStatusCode()== 300) {
//			
//		}
		
		DocumentVersion documentVersion = new DocumentVersion(document, masterCode, emp, comments);
		documentVersionService.changeStatusOne(documentVersion);
		
		//StatusLog statusLog = new StatusLog(emp, document, masterCode, comments);
		//statusLogService.registOne(statusLog);

	}

}
