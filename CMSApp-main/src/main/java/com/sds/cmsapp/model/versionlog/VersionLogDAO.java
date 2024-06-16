package com.sds.cmsapp.model.versionlog;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sds.cmsapp.domain.VersionLog;

@Mapper
public interface VersionLogDAO {

	public VersionLog select(int version_log_idx);
	
	public VersionLog selectByDocumentIdx(int document_idx);

}
