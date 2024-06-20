package com.sds.cmssetting.model.document;

import java.util.List;

import com.sds.cmssetting.domain.DocumentVersion;
import com.sds.cmssetting.domain.Emp;
import com.sds.cmssetting.domain.PublishedVersion;

public interface DocumentVersionService {
	
	public DocumentVersion selectByDocumentIdx(int document_idx); //resultMap="DocumentVersionMap"

	public void changeStatusOne(DocumentVersion documentVersion);
	
	public void changeStatusOfPublishedDocList(List<PublishedVersion> publishedVerList, Emp emp, String comments);
}
