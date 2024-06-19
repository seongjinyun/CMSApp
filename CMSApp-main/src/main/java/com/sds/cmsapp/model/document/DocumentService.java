package com.sds.cmsapp.model.document;

import java.util.List;
import java.util.Map;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.RequestDocFilterDTO;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.ResponseDocDTO;
import com.sds.cmsapp.domain.VersionLog;

public interface DocumentService {
	
	// 일반적인 select
	public Document select(final int documentIdx);
	
	// 범위만큼만 가져오기
	public List<Document> selectAllByRange(final Map<String, Integer> map);
	
	// 기존 selectAll
	public List<DocumentVersion> selectAllOrigin();
	
	/* 모든 문서 조회 */
	public List<Document> selectAll();
	
	/* 결재 상태별 문서 수 조회 */
	public Map<String, Integer> countByStatus();
	
	/* 결재 상태에 따라 문서 목록 조회 */
	public List<ResponseDocDTO> getFilteredList(RequestDocFilterDTO requestDTO);

	// 문서생성 + 버전
	public void documentInsert(VersionLog versionLog);
	
	// 문서 삭제 
	public int delete(int documentIdx);

	//document/list 파일 조회
	public List<DocumentVersion> documentListSelect(final Map<String, Integer> map);
	
	// 폴더 안의 문서들을 조회합니다
	public List<Document> selectByFolderIdx(int folder_idx); 
	
	//document/detail 문서 상세보기 
	public DocumentVersion documentDetailSelect(int documentIdx);

	//문서버전 업데이트
	public void versionUpdate(VersionLog versionLog);

	// DocumentDTO 안에 versionLog 채워넣기(제목 출력)
	public Document fillVersionLog(Document document);
	
	/**
	 * 배포된 버전인지 확인 후 boolean 반환
	 * @Return 배포됐으면 True 아니면 false
	 */
	public boolean isPublished(final int doucmentIdx);
	
	//DocumentVersion 의 상태 ( 리뷰요청 )
	public void documentVersionStatusUpdate(DocumentVersion documentVersion);
	
	//버전 조회
	public List<VersionLog> getVersionLogSelect(int documentIdx);

	//문서 버전 관리
	public int documentVersionUpdate(VersionLog versionLog);
	
	// 수정
	public int update(final Document document);
	
	// 버전 삭제
    public void versionLogDelete(int versionLog);


}
