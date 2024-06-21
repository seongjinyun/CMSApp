package com.sds.cmsapp.model.document;

import java.util.List;

import com.sds.cmsapp.domain.DocumentVersion;

public interface DocumentVersionService {
	
	public DocumentVersion selectByDocumentIdx(int document_idx); //resultMap="DocumentVersionMap"

	public void changeStatusOne(DocumentVersion documentVersion);
	
	public void changeStatusRejectedToDraft(DocumentVersion documentVersion);
	
	public void changeStatusOfPublishedDocList(List<DocumentVersion> publishingWaitingList);
}
