package com.sds.cmssettings.model.document;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmssettings.domain.DocStatus;
import com.sds.cmssettings.domain.Document;
import com.sds.cmssettings.domain.DocumentVersion;
import com.sds.cmssettings.domain.MasterCode;
import com.sds.cmssettings.domain.PublishedVersion;
import com.sds.cmssettings.exception.DocumentVersionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DocumentVersionServiceImpl implements DocumentVersionService {
	
	@Autowired
	private DocumentVersionDAO documentVersionDAO;

	//resultMap="DocumentVersionMap"
	public DocumentVersion selectByDocumentIdx(int document_idx) {
		return documentVersionDAO.selectByDocumentIdx(document_idx);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void changeStatusOfPublishedDoc(List<PublishedVersion> publishedVersionList)
		throws DocumentVersionException{
		
		for (PublishedVersion publishedVer : publishedVersionList) {
			Document doc = publishedVer.getDocument();
			DocumentVersion docVer = new DocumentVersion(doc, new MasterCode(DocStatus.PUBLISHED.getStatusCode()));
			
			// 상태 업데이트
			int resultOfUpdatingStatus = documentVersionDAO.updateStatusByDocumentIdx(docVer);
			if (resultOfUpdatingStatus > 0) log.debug("document_version 테이블의 상태 업데이트 성공");
			else throw new DocumentVersionException("document_version 테이블 상태 업데이트 실패");
		}
		
		
		
	}

}
