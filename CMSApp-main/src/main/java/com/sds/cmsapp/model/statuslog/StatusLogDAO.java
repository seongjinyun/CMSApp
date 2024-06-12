package com.sds.cmsapp.model.statuslog;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.FilterItemDTO;
import com.sds.cmsapp.domain.RequestDocumentDTO;
import com.sds.cmsapp.domain.ResponseDocumentCountDTO;
import com.sds.cmsapp.domain.StatusLog;

@Mapper
public interface StatusLogDAO {
	
	//public StatusLog select(int document_idx);
	
	/* 상태별 문서 수 조회 */
	public Integer countByStatus(int statusCode);
	
	/* 필터 조건에 따라 결재 진행 중인 문서 목록 조회 */
	public List<StatusLog> selectFilteredListOfLatestRegisteredLog(FilterItemDTO filterItemDTO);
	
	/* 결재 상태에 따라 문서 목록 조회 (10개만) */
	public List<StatusLog> selectSummaryListOfLatestRegisteredLog(int statusCode);
	
}
