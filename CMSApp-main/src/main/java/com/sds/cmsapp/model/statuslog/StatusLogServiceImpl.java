package com.sds.cmsapp.model.statuslog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.DocStatus;
import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.MasterCode;
import com.sds.cmsapp.domain.PublishedVersion;
import com.sds.cmsapp.domain.StatusLog;

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
	public void registPublishedLog(List<PublishedVersion> publishedVerList, String comments) {
		
		for (PublishedVersion publishedVer : publishedVerList) {
			
			Document doc = publishedVer.getDocument();
			Emp emp = new Emp(1); // 임시로 지정
			MasterCode masterCode = new MasterCode(DocStatus.PUBLISHED.getStatusCode());
			
			StatusLog statusLog = new StatusLog(emp, doc, masterCode, comments);
			
			int result = statusLogDAO.insert(statusLog);
			if (result > 0) {
				log.debug("상태 로그 삽입 성공");
			}
			
		}
	}

}

