package com.sds.cmsapp.model.document;

import com.sds.cmsapp.domain.DocumentVersion;

public interface DocumentVersionService {
	
	public DocumentVersion selectByDocumentIdx(int document_idx); //resultMap="DocumentVersionMap"
}
