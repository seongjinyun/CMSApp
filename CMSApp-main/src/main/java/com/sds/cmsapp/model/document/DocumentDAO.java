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
	
	public List selectAllForDashboard(Map map);

	// 선택 문서 조회
	public Document select(int document_idx); // returnType="Document"
	public Document selectByDocumentIdx(int document_idx); // returnMap="DocumentMap"

	// 문서 작성 폼	
	public void insert(VersionLog versionLog);
	
	public void documentInsert(Document document);
	//버전 생성
	public void versionInsert(VersionLog versionLog);
}