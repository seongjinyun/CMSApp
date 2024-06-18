package com.sds.cmsapp.model.document;

import java.util.List;

import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.PublishedVersion;

public interface DocumentVersionService {
	
	public DocumentVersion selectByDocumentIdx(int document_idx); //resultMap="DocumentVersionMap"

	public void changeStatusOne(DocumentVersion documentVersion);
	
	public void changeStatusOfPublishedDocList(List<PublishedVersion> publishedVerList);
}
