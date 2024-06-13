package com.sds.cmsapp.model.document;

import java.util.List;
import java.util.Map;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.RequestDocumentDTO;
import com.sds.cmsapp.domain.ResponseDocumentCountDTO;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.ResponseDocumentDTO;
import com.sds.cmsapp.domain.VersionLog;

public interface DocumentService {
	
	/* 결재 상태별 문서 수 조회 */
	public ResponseDocumentCountDTO countByStatus();
	
	/* 결재 상태에 따라 문서 목록 조회 (10개만, 휴지통에 있는 문서 제외) */
	public List<ResponseDocumentDTO> selectSummaryListOfCurrentStatus(int statusCode);
	
	/* 필터 조건에 따라 결재 진행 중인 문서 목록 조회 (휴지통에 있는 문서 제외) */
	public List<ResponseDocumentDTO> selectFilteredListOfCurrentStatus(RequestDocumentDTO requestDocumentDTO);

	// 선택 문서 조회
	public Document select(int documentIdx); // returnType="Document"
	
	public Document selectMap(int documentIdx); // association: VersionLog
	
	public List<Document> selectAll(Map map);

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
	
	// 폴더 idx 목록에 따른 문서 idx 목록 조회
	public List<Integer> selectDocumentIdxListInFolder(List<Integer> folderIdxList);

	//문서버전 업데이트
	public void versionUpdate(VersionLog versionLog);

	// DocumentDTO 안에 versionLog 채워넣기(제목 출력)
	public Document fillVersionLog(Document document);
}
