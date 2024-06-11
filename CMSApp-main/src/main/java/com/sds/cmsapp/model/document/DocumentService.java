package com.sds.cmsapp.model.document;

import java.util.List;
import java.util.Map;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.VersionLog;

public interface DocumentService {

	// 모든 문서 조회
	public List selectAll(Map map);
	
	public List selectAllForDashboard(Map map);
	
	// 결재 상태별 문서 수
	public int countForDashboard(int statusCode);

	// 선택 문서 조회
	public Document select(int documentIdx); // returnType="Document"

	public Document selectByDocumentIdx(int documentIdx); // returnMap="DocumentMap"

	// 문서생성 + 버전
	public void documentInsert(VersionLog versionLog);
	
	// 문서 삭제 
	public int delete(int documentIdx);

	//document/list 파일 조회
	public List documentListSelect(Map map);
	
	// 폴더 안의 문서들을 조회합니다
	public List<Document> selectByFolderIdx(int folder_idx); 
	
	//document/detail 문서 상세보기 
	public DocumentVersion documentDetailSelect(int documentIdx);
	

}
