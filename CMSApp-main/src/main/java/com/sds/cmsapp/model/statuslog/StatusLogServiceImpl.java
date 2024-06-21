package com.sds.cmsapp.model.statuslog;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.MasterCode;
import com.sds.cmsapp.domain.StatusLog;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.exception.StatusLogException;
import com.sds.cmsapp.model.document.DocumentVersionDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatusLogServiceImpl implements StatusLogService {
	
	@Autowired
	private StatusLogDAO statusLogDAO;
	
	@Autowired
	private DocumentVersionDAO documentVersionDAO;

	// 기록만을 위한 독립 테이블에 로그 추가
	@Transactional
	public void insertByDocumentVersion(DocumentVersion documentVersion) throws StatusLogException {
		
		int documentIdx = Optional.ofNullable(documentVersion.getDocument()).map(Document::getDocumentIdx).orElse(0);
		int versionLogIdx = Optional.ofNullable(documentVersionDAO.selectByDocumentIdx(documentIdx).getVersionLog()).map(VersionLog::getVersionLogIdx).orElse(0);
				
		int empIdx = Optional.ofNullable(documentVersion.getEmp()).map(Emp::getEmpIdx).orElse(0);
		int statusCode = Optional.ofNullable(documentVersion.getMasterCode()).map(MasterCode::getStatusCode).orElse(0);
		String comments = documentVersion.getStatusComments();
		
		StatusLog statusLog = new StatusLog(documentIdx, versionLogIdx, empIdx, statusCode, comments);
		
		int result = statusLogDAO.insert(statusLog);
		if (result > 0) log.debug("상태 로그 삽입 성공");
		else throw new StatusLogException("status_log 테이블에 배포 문서 로그 추가 실패");
	}

	public void insertByDocumentVersionList(List<DocumentVersion> documentVersionList)
		throws StatusLogException {
		
		for (DocumentVersion documentVersion : documentVersionList) {
			
			int documentIdx = Optional.ofNullable(documentVersion.getDocument()).map(Document::getDocumentIdx).orElse(0);
			int versionLogIdx = Optional.ofNullable(documentVersionDAO.selectByDocumentIdx(documentIdx).getVersionLog()).map(VersionLog::getVersionLogIdx).orElse(0);
					
			int empIdx = Optional.ofNullable(documentVersion.getEmp()).map(Emp::getEmpIdx).orElse(0);
			int statusCode = Optional.ofNullable(documentVersion.getMasterCode()).map(MasterCode::getStatusCode).orElse(0);
			String comments = documentVersion.getStatusComments();
			
			StatusLog statusLog = new StatusLog(documentIdx, versionLogIdx, empIdx, statusCode, comments);
			
			int result = statusLogDAO.insert(statusLog);
			if (result > 0) log.debug("상태 로그 삽입 성공");
			else throw new StatusLogException("status_log 테이블에 배포 문서 로그 추가 실패");
			
		}
	}

}

