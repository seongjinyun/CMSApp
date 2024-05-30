package com.sds.cmsapp.model.status;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.StatusLog;

@Mapper
public interface StatusLogDAO {
	public StatusLog selectByStatusLogIdx(int status_log_idx);
	public StatusLog selectLatestChangedByDocumentIdx(int document_idx);
	
	public List selectAllByDocumentIdx(int document_idx);
	public List selectAllByStatusCode(int status_code);
}
