package com.sds.cmsapp.model.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.StatusLog;

@Service
public class StatusLogServiceImpl implements StatusLogService {
	
	@Autowired
	private StatusLogDAO statusLogDAO;

	@Override
	public StatusLog selectByStatusLogIdx(int status_log_idx) {
		return statusLogDAO.selectByStatusLogIdx(status_log_idx);
	}

	@Override
	public StatusLog selectLatestChangedByDocumentIdx(int document_idx) {
		return statusLogDAO.selectLatestChangedByDocumentIdx(document_idx);
	}
	
	public List selectAllByDocumentIdx(int document_idx) {
		return statusLogDAO.selectAllByDocumentIdx(document_idx);
	}

	@Override
	public List selectAllByStatusCode(int status_code) {
		return statusLogDAO.selectAllByStatusCode(status_code);
	}

}
