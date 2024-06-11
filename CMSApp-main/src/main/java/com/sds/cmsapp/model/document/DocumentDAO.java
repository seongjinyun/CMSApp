package com.sds.cmsapp.model.document;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.VersionLog;

@Mapper
public interface DocumentDAO {

	// 모든 문서 조회
	public List selectAll(Map map);
	
	public List selectAllForDashboard(Map map);
	
	// 결재 상태별 문서 수
	public int countForDashboard(int statusCode);

	// 선택 문서 조회
	public Document select(int documentIdx); // returnType="Document"
	public Document selectByDocumentIdx(int documentIdx); // returnMap="DocumentMap"
	public List<Document> selectByFolderIdx(int folderIdx); // mybatis 연결 부탁드려요~ (박준형)

	
	public int documentInsert(Document document);
	//버전 생성
	public int versionInsert(VersionLog versionLog);

	//문서 현재 버전 테이블 
	public int documentVersionInsert(DocumentVersion documentVersion);
	
	//파일 리스트 조회
	public List documentListSelect(Map map);
	
	// 문서 수정
	public int update(Document document);
	
	
	// 문서 삭제 (임시로 만들어뒀습니다 -박준형)
	public int delete(int documentIdx);
}