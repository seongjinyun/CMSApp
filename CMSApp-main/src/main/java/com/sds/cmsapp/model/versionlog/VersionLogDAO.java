package com.sds.cmsapp.model.versionlog;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sds.cmsapp.domain.VersionLog;

@Mapper
public interface VersionLogDAO {

	@Select("SELECT * FROM version_log WHERE version_log_idx = #{versionLogIdx}")
	public VersionLog select(int version_log_idx);
	
	public VersionLog selectByDocumentIdx(int document_idx);

}
