package com.sds.cmsapp.model.document;

import java.util.List;
import java.util.Map;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.VersionLog;

public interface DocumentService {

	// 모든 문서 조회
	public List selectAll(Map map);

	// 선택 문서 조회
	public Document select(int document_idx); // returnType="Document"

	public Document selectByDocumentIdx(int document_idx); // returnMap="DocumentMap"

	// 문서 작성 폼
	public void insert(VersionLog versionLog);

	// 문서생성 + 버전
	public void documentInsert(Document document, VersionLog versionLog);
	
	// 문서 삭제 
	public int delete(int document_idx);

}
