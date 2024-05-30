package com.sds.cmsapp.model.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.VersionLog;

@Service
public class DocumentServiceImpl implements DocumentService {
	
	@Autowired
	private DocumentDAO documentDAO;

	// returnType="Document"
	public Document select(int document_idx) {
		return documentDAO.select(document_idx);
	}

	// returnMap="DocumentMap"
	public Document selectByDocumentIdx(int document_idx) {
		return documentDAO.selectByDocumentIdx(document_idx);
	}
	
	public void insert(VersionLog versionLog) {
		documentDAO.insert(versionLog);
	}

}
