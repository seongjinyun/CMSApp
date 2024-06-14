package com.sds.cmsapp.model.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.Folder;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.exception.DocumentException;
import com.sds.cmsapp.exception.TrashException;
import com.sds.cmsapp.exception.VersionLogException;
import com.sds.cmsapp.model.folder.FolderDAO;
import com.sds.cmsapp.model.versionlog.PublishedVersionDAO;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {
	
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private DocumentVersionDAO documentVersionDAO;

	@Autowired
	private DocumentDetailDAO documentDetailDAO;
	
	@Autowired
	private PublishedVersionDAO publishedVersionDAO;
	
	@Autowired
	private FolderDAO folderDAO;

	
	// 모든 문서 조회
	public List<DocumentVersion> selectAll() {
		List<DocumentVersion> resultList = new ArrayList<>();
		List<Folder> topFolderList = folderDAO.selectTopFolder();
		for(Folder folder : topFolderList) {
			Map<String, Integer> map = new HashMap<>();
			map.put("folderIdx", folder.getFolderIdx());
			resultList.addAll(documentDAO.documentListSelect(map));
		}
		return resultList;
	};
	
	public List<Document> selectAllByRange(final Map<String, Integer> map){
		return documentDAO.selectAllByRange(map);
	}
	
	public List selectAllForDashboard(Map map) {
		return documentDAO.selectAllForDashboard(map);
	};
	
	// 결재 상태별 문서 수
	public int countForDashboard(int statusCode) {
		return documentDAO.countForDashboard(statusCode);
	};

	// 선택 문서 조회
	
	// returnType="Document"
	public Document select(int documentIdx) {
		return documentDAO.select(documentIdx);
	}; 
	
	// returnMap="DocumentMap"
	public Document selectByDocumentIdx(int documentIdx) {
		return documentDAO.selectByDocumentIdx(documentIdx);
	}; 
	
	//새로운 문서 작성
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
	
	//document/detail 문서 상세보기 
	public DocumentVersion documentDetailSelect(int documentIdx) {
	    
        return documentDetailDAO.documentDetailSelect(documentIdx);
	}
	
	/**
	 * 문서와 관련된 모든 레코드를 영구삭제하는 메서드.
	 * @param 삭제할 문서의 documentIdx
	 * @return 삭제된 문서의 수
	 * @throws TrashException 트랜잭션 중 하나라도 실패하면 발생합니다
	 */
	@Override
	public int delete(int documentIdx) {
		return documentDAO.delete(documentIdx);
	}
	
	@Override // 박준형 추가
	public List<Document> selectByFolderIdx(int folderIdx) {
		return documentDAO.selectByFolderIdx(folderIdx);
	}

	@Transactional
	public void versionUpdate(VersionLog versionLog) throws VersionLogException{
		
		DocumentVersion documentVersion = documentDetailDAO.documentDetailSelect(versionLog.getDocument().getDocumentIdx());
		log.debug("versionLog = "+documentVersion.getVersionLog());
		
		int result = documentDetailDAO.versionLogInsert(documentVersion.getVersionLog());
		
		if(result < 1) {
			throw new VersionLogException("문서 버전 로그 등록실패 ");
		}
		
		int versionIdx = Integer.parseInt(documentVersion.getVersionLog().getVersion());
		String newVersion = Integer.toString(versionIdx + 1);
		documentVersion.getVersionLog().setVersion(newVersion);
		
		result = documentDetailDAO.versionUpdate(documentVersion.getVersionLog());
		
		log.debug("version = " + documentVersion.getVersionLog().getVersion());
		if(result < 1) {
			throw new VersionLogException("문서 버전 증가 실패 ");
		}
		
		result = documentDetailDAO.documentVersionUpdate(documentVersion.getVersionLog());
		
		if(result < 1) {
			throw new DocumentException("문서 현재 버전 업데이트 실패 ");
		}
	}
	@Override
	public Document fillVersionLog(final Document document) {
		DocumentVersion documentVersion = documentVersionDAO.selectByDocumentIdx(document.getDocumentIdx());
		VersionLog versionLog = documentVersion.getVersionLog();
		document.setVersionLog(versionLog);
		return document;
	}
	
	@Override
	public boolean isPublished(int doucmentIdx) {
		boolean flag = false;
		if(publishedVersionDAO.selectByDocumentIdx(doucmentIdx) == null) {
			flag = true;
		}
		return flag;
	}
}
