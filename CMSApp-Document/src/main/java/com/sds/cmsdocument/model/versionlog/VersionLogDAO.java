package com.sds.cmsdocument.model.versionlog;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsdocument.domain.VersionLog;

@Mapper
public interface VersionLogDAO {

	public VersionLog select(int version_log_idx);
	
	public int insert(final VersionLog versionLog);
	
	public int delete(final int versionLogDAO);
	
	public int deleteByDocumentIdx(final int versionLogDAO);
	
	public VersionLog selectByDocumentIdx(int document_idx);

}
