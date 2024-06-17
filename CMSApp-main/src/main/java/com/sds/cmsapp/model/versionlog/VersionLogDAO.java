package com.sds.cmsapp.model.versionlog;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sds.cmsapp.domain.VersionLog;

@Mapper
public interface VersionLogDAO {

	public VersionLog select(int version_log_idx);
	
	public int insert(final VersionLog versionLog);
	
	public int delete(final int versionLogDAO);
	
	public int deleteByDocumentIdx(final int versionLogDAO);
	
	public VersionLog selectByDocumentIdx(int document_idx);

}
