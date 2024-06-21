package com.sds.cmsdocument.model.publishing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsdocument.domain.Document;
import com.sds.cmsdocument.domain.DocumentVersion;
import com.sds.cmsdocument.domain.PublishedVersion;
import com.sds.cmsdocument.domain.PublishedVersionName;
import com.sds.cmsdocument.domain.VersionLog;
import com.sds.cmsdocument.exception.PublishedVersionException;
import com.sds.cmsdocument.exception.PublishedVersionNameException;
import com.sds.cmsdocument.model.document.DocumentDAO;
import com.sds.cmsdocument.model.document.DocumentVersionDAO;
import com.sds.cmsdocument.model.trash.TrashDAO;
import com.sds.cmsdocument.model.versionlog.VersionLogDAO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PublishedVersionServiceImpl implements PublishedVersionService {
	
	@Autowired
	PublishedVersionNameDAO publishedVersionNameDAO;
	
	@Autowired
	DocumentDAO documentDAO;
	
	@Autowired
	VersionLogDAO versionLogDAO;
	
	@Autowired
	TrashDAO trashDAO;
	
	@Autowired
	DocumentVersionDAO documentVersionDAO;
	
	@Autowired
	PublishedVersionDAO publishedVersionDAO;
	
	// 배포판 생성
	@Transactional(propagation = Propagation.REQUIRED)
	public PublishedVersionName registPublishedVersionName(PublishedVersionName publishedVersionName) {
		log.debug("service가 전달받은 배포판 이름: " + publishedVersionName.getPublishedVersionName());
		
		// 배포판 이름 생성
		int result = publishedVersionNameDAO.insert(publishedVersionName);
		if (result > 0) log.debug("배포판 이름 추가 성공");
		else throw new PublishedVersionNameException("배포판 생성 실패");
		return publishedVersionName;
	}
	
	// 배포 대기 문서 목록 생성
	@Transactional(propagation = Propagation.REQUIRED)
	public List<PublishedVersion> selectWaitingQueue()
		throws PublishedVersionException {
		List<PublishedVersion> publishedVerList = new ArrayList<PublishedVersion>(); 		// 배포판 테이블에 배포할 문서 목록 생성
		
		List<Document> docList = documentDAO.selectAll(); // 문제..Folder 정보까지 가져오게 됨.. 
		if (docList == null) throw new PublishedVersionException("배포할 문서가 없습니다.");
		for (Document doc : docList) {
			int docIdx = doc.getDocumentIdx(); // 문서 번호
			if (trashDAO.selectTypeByDocumentIdx(docIdx) != null) {
				log.debug(docIdx + "번 문서는 휴지통에 있는 문서입니다.");
				continue;
			}
			
			// 버전 정보와 상태 정보를 가진 DocumentVersion 한 건 조회
			DocumentVersion docVer = documentVersionDAO.selectByDocumentIdx(docIdx); // DocumentVersionMap > MasterCode, VersionLog
			int statusCode = docVer.getMasterCode().getStatusCode();
			int versionLogIdx = docVer.getVersionLog().getVersionLogIdx();
			VersionLog versionLog = versionLogDAO.select(versionLogIdx);
			log.debug(docIdx + "번, 결재 상태 코드: " + statusCode + ", 버전 로그 번호: " + versionLogIdx);
			
			// 상태 코드가 300(리뷰 완료)가 아닌 문서 제외
			if (statusCode == 200 && statusCode == 500 && statusCode == 0) {
				log.debug("배포 대기 목록에서 제외");
				continue;
			}
			
//			if (statusCode == 100 
//					&& versionLogIdx == publishedVersionDAO.selectByDocumentIdx(docIdx).getVersionLog().getVersionLogIdx() 
//					&& publishedVersionDAO.selectByDocumentIdx(docIdx) != null) {
//				log.debug("기 배포 목록");
//			}
			
			if(statusCode == 300) {
				log.debug("리뷰 완료 목록");
			}
			PublishedVersion publishedVer = new PublishedVersion(doc, versionLog);
			publishedVerList.add(publishedVer);
		}
		log.debug("배포 대기 목록의 총 문서 수: " + publishedVerList.size());
		if (publishedVerList.size() == 0) throw new PublishedVersionException("배포할 문서가 없습니다.");
		return publishedVerList;
	}
	
	// 배포 대기 문서 목록 생성 (오버로딩)
	@Transactional(propagation = Propagation.REQUIRED)
	public List<PublishedVersion> selectWaitingQueue(PublishedVersionName publishedVersionName)
		throws PublishedVersionException {
		List<PublishedVersion> publishedVerList = new ArrayList<PublishedVersion>(); 		// 배포판 테이블에 배포할 문서 목록 생성
		
		List<Document> docList = documentDAO.selectAll(); // 문제..Folder 정보까지 가져오게 됨.. 
		if (docList == null) throw new PublishedVersionException("배포할 문서가 없습니다.");
		for (Document doc : docList) {
			int docIdx = doc.getDocumentIdx(); // 문서 번호
			if (trashDAO.selectTypeByDocumentIdx(docIdx) != null) {
				log.debug(docIdx + "번 문서는 휴지통에 있는 문서입니다.");
				continue;
			}
			
			// 버전 정보와 상태 정보를 가진 DocumentVersion 한 건 조회
			DocumentVersion docVer = documentVersionDAO.selectByDocumentIdx(docIdx); // DocumentVersionMap > MasterCode, VersionLog
			int statusCode = docVer.getMasterCode().getStatusCode();
			int versionLogIdx = docVer.getVersionLog().getVersionLogIdx();
			VersionLog versionLog = versionLogDAO.select(versionLogIdx);
			log.debug(docIdx + "번, 결재 상태 코드: " + statusCode + ", 버전 로그 번호: " + versionLogIdx);
			
			// 상태 코드가 300(리뷰 완료)이 아닌 문서 제외
			if (statusCode == 200 && statusCode == 500 && statusCode == 0) {
				log.debug("배포 대기 목록에서 제외");
				continue;
			}
			
//			if (statusCode == 100 
//					&& versionLogIdx == publishedVersionDAO.selectByDocumentIdx(docIdx).getVersionLog().getVersionLogIdx() 
//					&& publishedVersionDAO.selectByDocumentIdx(docIdx) != null) {
//				log.debug("기 배포 목록");
//			}
			
			if(statusCode == 300) {
				log.debug("리뷰 완료 목록");
			}
			PublishedVersion publishedVer = new PublishedVersion(doc, versionLog, publishedVersionName);
			publishedVerList.add(publishedVer);
		}
		log.debug("배포 대기 목록의 총 문서 수: " + publishedVerList.size());
		if (publishedVerList.size() == 0) throw new PublishedVersionException("배포할 문서가 없습니다.");
		return publishedVerList;
	}
	
	// 배포 테이블에 배포 대기 문서 추가
	@Transactional(propagation = Propagation.REQUIRED)
	public void registPublishedVersion(List<PublishedVersion> publishedVerList) 
		throws PublishedVersionException {
		for (PublishedVersion publishedVer : publishedVerList) {
			int result = publishedVersionDAO.insert(publishedVer);
			if (result > 0) log.debug(publishedVer.getDocument().getDocumentIdx() + "번 문서 배포 테이블에 추가 성공");
			else throw new PublishedVersionException("배포판에 문서 추가 실패");
		}
	}
	
}
