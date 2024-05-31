package com.sds.cmsapp.model.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.DocumentVersion;

@Service
public class DocumentVersionServiceImpl implements DocumentVersionService {
	
	@Autowired
	private DocumentVersionDAO documentVersionDAO;

	//resultMap="DocumentVersionMap"
	public DocumentVersion selectByDocumentIdx(int document_idx) {
		return documentVersionDAO.selectByDocumentIdx(document_idx);
	}

}
