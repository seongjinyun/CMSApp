package com.sds.cmsapp.model.document;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.VersionLog;

@Service
public class DocumentServiceImpl implements DocumentService {
	
	@Autowired
	private DocumentDAO documentDAO;
	
	// 모든 문서 조회
	public List selectAll(Map map) {
		return null;
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
    public void documentInsert(Document document, VersionLog versionLog) {
        // 문서 삽입
		documentDAO.documentInsert(document);
        // versionLog에 document 설정
        versionLog.setDocument(document);
        // 버전 로그 삽입
        documentDAO.versionInsert(versionLog);
        
        System.out.println(versionLog.getContent());
    }

	@Override
	public void insert(VersionLog versionLog) {
		// TODO Auto-generated method stub
		
	}
	
	@Override // 임시로 만들어뒀습니다 -박준형
	public int delete(int document_idx) {
		return documentDAO.delete(document_idx);
	}

}
