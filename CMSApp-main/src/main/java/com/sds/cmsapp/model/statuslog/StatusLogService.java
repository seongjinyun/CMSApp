package com.sds.cmsapp.model.statuslog;

import java.util.List;

import com.sds.cmsapp.domain.FilterItemDTO;
import com.sds.cmsapp.domain.RequestDocFilterDTO;
import com.sds.cmsapp.domain.ResponseDocCountDTO;
import com.sds.cmsapp.domain.StatusLog;

public interface StatusLogService {
	
//	public StatusLog select(int document_idx);
	
	/* 상태별 문서 수 조회 */
	//public Integer countByStatus(int statusCode);
	
	/* 필터 조건에 따라 결재 진행 중인 문서 목록 조회 */
	//public List<StatusLog> selectFilteredListOfLatestRegisteredLog(FilterItemDTO filterItemDTO);
	
	/* 결재 상태에 따라 문서 목록 조회 (10개만) */
	//public List<StatusLog> selectSummaryListOfLatestRegisteredLog(int statusCode);

	public StatusLog select(int documentIdx);
}
