package com.sds.cmsapp.model.statuslog;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.StatusLog;

@Mapper
public interface StatusLogDAO {
	
	public StatusLog select(int document_idx);
	
	// 해당 문서에 대하여 가장 마지막에 등록된 상태 로그
	public StatusLog selectLatestRegisteredStatusLogByDocumentIdx(int document_idx);
	
}
