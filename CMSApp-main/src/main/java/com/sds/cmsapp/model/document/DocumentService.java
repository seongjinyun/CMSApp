package com.sds.cmsapp.model.document;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.VersionLog;

public interface DocumentService {
	public Document select(int document_idx); // returnType="Document"
	public Document selectByDocumentIdx(int document_idx); // returnMap="DocumentMap"
	
	public void insert(VersionLog versionLog);
}
