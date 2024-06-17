package com.sds.cmsapp.model.statuslog;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.sds.cmsapp.domain.StatusLog;

@Mapper
public interface StatusLogDAO {
	
	public StatusLog select(final int documentIdx);
	
	// 해당 문서에 대하여 가장 마지막에 등록된 상태 로그 조회 (statusLogMap)
	public StatusLog selectLatestLogByDocumentIdx(final int documentIdx);
	
	// 삽입
	public int insert(final StatusLog statusLog);
	
	// 삭제
	public int deleteByDocumentIdx(final int documentIdx);
	
}
