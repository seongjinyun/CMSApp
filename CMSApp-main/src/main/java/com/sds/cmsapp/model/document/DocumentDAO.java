package com.sds.cmsapp.model.document;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.VersionLog;

@Mapper
public interface DocumentDAO {
	public Document select(int document_idx); // returnType="Document"
	public Document selectByDocumentIdx(int document_idx); // returnMap="DocumentMap"
	
<<<<<<< HEAD
	public void insert(VersionLog versionLog);
}
=======
	public void documentInsert(Document document);
	//버전 생성
	public void versionInsert(VersionLog versionLog);
}

>>>>>>> 823e578f7f4529bfe40446c5334af12b2758ef90
