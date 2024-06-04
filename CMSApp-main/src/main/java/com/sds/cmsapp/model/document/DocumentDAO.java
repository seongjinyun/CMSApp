package com.sds.cmsapp.model.document;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.VersionLog;

@Mapper
public interface DocumentDAO {

	// 모든 문서 조회
	public List selectAll(Map map);

	// 선택 문서 조회
	public Document select(int document_idx); // returnType="Document"
	public Document selectByDocumentIdx(int document_idx); // returnMap="DocumentMap"
	public List<Document> selectByFolderIdx(int folder_idx); // mybatis 연결 부탁드려요~ (박준형)

	
	public int documentInsert(Document document);
	//버전 생성
	public int versionInsert(VersionLog versionLog);
	
	//파일 리스트 조회
	public List documentListSelect(Map map);
	
	// 문서 수정
	public int update(Document document);
}