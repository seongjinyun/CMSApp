package com.sds.cmsdocument.model.document;

import java.util.List;

import com.sds.cmsdocument.domain.DocumentVersion;
import com.sds.cmsdocument.domain.Emp;
import com.sds.cmsdocument.domain.PublishedVersion;

public interface DocumentVersionService {
	
	public DocumentVersion selectByDocumentIdx(int document_idx); //resultMap="DocumentVersionMap"

	public void changeStatusOne(DocumentVersion documentVersion);
	
	public void changeStatusRejectedToDraft(DocumentVersion documentVersion);

	public void changeStatusOfPublishedDocList(List<DocumentVersion> publishingWaitingList);
	
}
