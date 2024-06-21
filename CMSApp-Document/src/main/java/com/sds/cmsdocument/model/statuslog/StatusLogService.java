package com.sds.cmsdocument.model.statuslog;

import java.util.List;

import com.sds.cmsdocument.domain.DocumentVersion;

public interface StatusLogService {
	
	// 기록만을 위한 독립 테이블에 로그 추가
	public void insertByDocumentVersion(DocumentVersion documentVersion);
	
	public void insertByDocumentVersionList(List<DocumentVersion> documentVersionList);

}
