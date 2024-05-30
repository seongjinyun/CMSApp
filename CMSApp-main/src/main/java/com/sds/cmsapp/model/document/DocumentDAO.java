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
	public List selectAll();

	// 선택 문서 조회
	public Document selectByDocumentIdx(int document_idx);
	
	// 문서 작성 폼
<<<<<<< HEAD
	public void versionInsert(VersionLog versionLog);
}
=======
	public void insert(VersionLog versionLog);
}
>>>>>>> 6896369092883c476576c2aebe26cdf5bde5fb6b
