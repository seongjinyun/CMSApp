package com.sds.cmsapp.model.statuslog;

import java.util.List;

import com.sds.cmsapp.domain.StatusLog;

public interface StatusLogService {
	
	public StatusLog select(int document_idx);
	
	// 해당 문서에 대하여 가장 마지막에 등록된 상태 로그
	public StatusLog selectLatestRegisteredStatusLogByDocumentIdx(int document_idx);
}
