package com.sds.cmsapp.model.document;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.VersionLog;

@Mapper
public interface DocumentDAO {
	
	// 전체 문서 수 조회
	public int selectCount();
	
	// 모든 문서 조회
	public List selectAll(Map map);
	
	// 선택 문서 조회
	public Document select(int document_idx);
	
	// 문서 작성 폼
	public void insert(VersionLog versionLog);
}
