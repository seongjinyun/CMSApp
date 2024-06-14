package com.sds.cmsapp.model.document;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.VersionLog;

@Mapper
public interface DocumentDAO {

	@Select("SELECT * FROM DOCUMENT")
	@ResultMap("DocumentMap")
	public List<Document> selectAll(); // 전체 문서 목록 조회

	@Select("SELECT * FROM document WHERE document_idx = #{documentIdx}")
	@ResultMap("DocumentMap")
	public Document select(int documentIdx); // 선택 문서 조회
	
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