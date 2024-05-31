package com.sds.cmsapp.model.statuslog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.StatusLog;

@Service
public class StatusLogServiceImpl implements StatusLogService {
	
	@Autowired
	private StatusLogDAO statusLogDAO;

	// 해당 문서에 대하여 가장 마지막에 등록된 상태 로그
	public StatusLog selectLatestRegisteredStatusLogByDocumentIdx(int document_idx) {
		return statusLogDAO.selectLatestRegisteredStatusLogByDocumentIdx(document_idx);
	}
	
}

