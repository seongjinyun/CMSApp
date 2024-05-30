package com.sds.cmsapp.model.status;

import java.util.List;

import com.sds.cmsapp.domain.StatusLog;

public interface StatusLogService {
	public StatusLog selectByStatusLogIdx(int status_log_idx);
	public StatusLog selectLatestChangedByDocumentIdx(int document_idx);
	
	public List selectAllByDocumentIdx(int document_idx);
	public List selectAllByStatusCode(int status_code);
}
