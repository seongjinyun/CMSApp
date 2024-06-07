package com.sds.cmsapp.model.document;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.exception.DocumentException;
import com.sds.cmsapp.exception.VersionLogException;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {
	
	@Autowired
	private DocumentDAO documentDAO;
	
	// 모든 문서 조회
	public List selectAll(Map map) {
		return null;
	};
	
	public List selectAllForDashboard(Map map) {
		return documentDAO.selectAllForDashboard(map);
	};
	
	// 결재 상태별 문서 수
	public int countForDashboard(int status_code) {
		return documentDAO.countForDashboard(status_code);
	};

	// 선택 문서 조회
	
	// returnType="Document"
	public Document select(int document_idx) {
		return documentDAO.select(document_idx);
	}; 
	
	// returnMap="DocumentMap"
	public Document selectByDocumentIdx(int document_idx) {
		return documentDAO.selectByDocumentIdx(document_idx);
	}; 
	
	@Transactional
    public void documentInsert(VersionLog versionLog) throws DocumentException, VersionLogException {
        // 문서 삽입
		int result = documentDAO.documentInsert(versionLog.getDocument());
		
		log.debug("document reuslt is "+result);
		
		if(result < 1) {
			throw new DocumentException("문서 insert 실패 ");
		}
		
        result = documentDAO.versionInsert(versionLog);
        
       // log.debug("versionLog reuslt is "+result);
       // log.debug("document _idx is "+versionLog.getDocument().getDocument_idx());
        
		if(result < 1) {
			throw new VersionLogException("문서 버전 로그 등록실패 ");
		}
		
		DocumentVersion documentVersion = new DocumentVersion();
		documentVersion.setDocument(versionLog.getDocument());
		documentVersion.setVersionLog(versionLog);
		
		//log.debug("document_version is " + documentVersion);
		
		result = documentDAO.documentVersionInsert(documentVersion);
		
		if(result < 1) {
			throw new DocumentException("문서 현재 버전 insert 실패 ");
		}
    }

	@Override
	public List documentListSelect(Map map) {
		
		return documentDAO.documentListSelect(map);
	}
	
	@Override // 임시로 만들어뒀습니다 -박준형
	public int delete(int document_idx) {
		return documentDAO.delete(document_idx);
	}


}
