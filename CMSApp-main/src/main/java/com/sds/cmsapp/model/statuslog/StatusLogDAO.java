package com.sds.cmsapp.model.statuslog;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.sds.cmsapp.domain.FilterItemDTO;
import com.sds.cmsapp.domain.RequestDocFilterDTO;
import com.sds.cmsapp.domain.ResponseDocCountDTO;
import com.sds.cmsapp.domain.StatusLog;

@Mapper
public interface StatusLogDAO {
	
	//public StatusLog select(int document_idx);
	
	/* 상태별 문서 수 조회 */
	//public Integer countByStatus(int statusCode);
	
	/* 필터 조건에 따라 결재 진행 중인 문서 목록 조회 */
	//public List<StatusLog> selectFilteredListOfLatestRegisteredLog(FilterItemDTO filterItemDTO);
	
	/* 결재 상태에 따라 문서 목록 조회 (10개만) */
	//public List<StatusLog> selectSummaryListOfLatestRegisteredLog(int statusCode);

	public StatusLog select(int documentIdx);
	
	// 해당 문서에 대하여 가장 마지막에 등록된 상태 로그
	@Select("SELECT * FROM status_log WHERE (document_idx, status_log_idx)"
			+ "IN (SELECT document_idx, MAX(status_log_idx) FROM status_log WHERE document_idx = #{documentIdx})")
	@ResultMap("StatusLogMap")
	public StatusLog selectLatestLogByDocumentIdx(int documentIdx);
	
	// 삭제
	public int deleteByDocumentIdx(final int documentIdx);
	
}
