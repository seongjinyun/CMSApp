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

	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List selectAll(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document selectByDocumentIdx(int document_idx) {
		return documentDAO.selectByDocumentIdx(document_idx);
	}
	
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

}