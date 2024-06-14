package com.sds.cmsapp.model.versionlog;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.VersionLog;

@Mapper
public interface VersionLogDAO {
	
	// 하나 조회 by version_log_idx
	public VersionLog selectByVersionLogIdx(int version_log_idx);
	
	// 0603 추가
	public VersionLog select(int version_log_idx);
	
	public int insert(final VersionLog versionLog);
	
	public int delete(final int versionLogDAO);
	
	public int deleteByDocumentIdx(final int versionLogDAO);

}
