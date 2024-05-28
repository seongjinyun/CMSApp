package com.sds.cmsapp.model.document;

import java.util.List;
import java.util.Map;

import com.sds.cmsapp.domain.Document;

public interface DocumentService {

	// 전체 문서 수 조회
	public int selectCount();
	
	// 모든 문서 조회
	public List selectAll(Map map);
	
	// 선택 문서 조회
	public Document select(int document_idx);
}
