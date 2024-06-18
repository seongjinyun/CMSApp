package com.sds.cmssettings.model.document;

import java.util.List;

import com.sds.cmssettings.domain.DocumentVersion;
import com.sds.cmssettings.domain.PublishedVersion;

public interface DocumentVersionService {
	
	public DocumentVersion selectByDocumentIdx(int document_idx); //resultMap="DocumentVersionMap"

	public void changeStatusOfPublishedDoc(List<PublishedVersion> publishedVerList);
}
