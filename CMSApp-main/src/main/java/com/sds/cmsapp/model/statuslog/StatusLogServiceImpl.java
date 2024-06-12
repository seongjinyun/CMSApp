package com.sds.cmsapp.model.statuslog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.FilterItemDTO;
import com.sds.cmsapp.domain.RequestDocumentDTO;
import com.sds.cmsapp.domain.ResponseDocumentCountDTO;
import com.sds.cmsapp.domain.StatusLog;

@Service
public class StatusLogServiceImpl implements StatusLogService {
	
	@Autowired
	private StatusLogDAO statusLogDAO;
	
//	public StatusLog select(int document_idx) {
//		return statusLogDAO.select(document_idx);
//	};
	
	/* 상태별 문서 수 조회 */
	public Integer countByStatus(int statusCode) {
		return statusLogDAO.countByStatus(statusCode);
	};
	
	/* 결재 상태에 따라 문서 목록 조회 (10개만) */
	public List<StatusLog> selectSummaryListOfLatestRegisteredLog(int statusCode) {
		return statusLogDAO.selectSummaryListOfLatestRegisteredLog(statusCode);
	};

	/* 필터 조건에 따라 결재 진행 중인 문서 목록 조회 */
	public List<StatusLog> selectFilteredListOfLatestRegisteredLog(FilterItemDTO filterItemDTO) {
		return statusLogDAO.selectFilteredListOfLatestRegisteredLog(filterItemDTO);
	};
	
}

