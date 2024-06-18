package com.sds.cmsapp.model.statuslog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.DocStatus;
import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.MasterCode;
import com.sds.cmsapp.domain.PublishedVersion;
import com.sds.cmsapp.domain.StatusLog;
import com.sds.cmsapp.exception.StatusLogException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatusLogServiceImpl implements StatusLogService {
	
	@Autowired
	private StatusLogDAO statusLogDAO;
	
//	public StatusLog select(int documentIdx) {
//		return statusLogDAO.select(documentIdx);
//	};
	
	@Transactional
	public void registOne(StatusLog statusLog) throws StatusLogException {
		int result = statusLogDAO.insert(statusLog);
		if (result > 0) log.debug("상태 로그 삽입 성공");
		else throw new StatusLogException("status_log 테이블에 배포 문서 로그 추가 실패");
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void registPublishedLog(List<PublishedVersion> publishedVerList, String comments, Emp emp)
		throws StatusLogException {
		
		for (PublishedVersion publishedVer : publishedVerList) {
			
			Document doc = publishedVer.getDocument();
			MasterCode masterCode = new MasterCode(DocStatus.PUBLISHED.getStatusCode());
			
			StatusLog statusLog = new StatusLog(emp, doc, masterCode, comments);
			
			int result = statusLogDAO.insert(statusLog);
			if (result > 0) log.debug("상태 로그 삽입 성공");
			else throw new StatusLogException("status_log 테이블에 배포 문서 로그 추가 실패");
			
		}
	}

}

