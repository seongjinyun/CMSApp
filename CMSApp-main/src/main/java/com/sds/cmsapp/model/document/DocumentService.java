package com.sds.cmsapp.model.document;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.VersionLog;

public interface DocumentService {
	public Document select(int document_idx); // returnType="Document"
	public Document selectByDocumentIdx(int document_idx); // returnMap="DocumentMap"
	
<<<<<<< HEAD
	public void insert(VersionLog versionLog);
=======
	//문서생성 + 버전
	public void documentInsert(Document document, VersionLog versionLog);
	
>>>>>>> 823e578f7f4529bfe40446c5334af12b2758ef90
}
