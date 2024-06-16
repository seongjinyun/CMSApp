package com.sds.cmsapp.model.publishing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.PublishedVersion;
import com.sds.cmsapp.domain.PublishedVersionName;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.exception.PublishedVersionException;
import com.sds.cmsapp.exception.PublishedVersionNameException;
import com.sds.cmsapp.model.document.DocumentDAO;
import com.sds.cmsapp.model.document.DocumentVersionDAO;
import com.sds.cmsapp.model.trash.TrashDAO;
import com.sds.cmsapp.model.versionlog.VersionLogDAO;

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
	@Transactional
	public PublishedVersionName registPublishedVersionName(PublishedVersionName publishedVersionName) {
		log.debug("service가 전달받은 배포판 이름: " + publishedVersionName.getPublishedVersionName());
		
		// 배포판 이름 생성
		int result = publishedVersionNameDAO.insert(publishedVersionName);
		if (result > 0) log.debug("배포판 이름 추가 성공");
		else throw new PublishedVersionNameException("배포판 생성 실패");
		return publishedVersionName;
	}
	
	// 배포 대기 문서 목록 생성
	@Transactional
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
			
			// 상태 코드가 300(리뷰 완료) 또는 400(배포 완료)가 아닌 문서 제외
			if (statusCode != 300 && statusCode != 400) {
				log.debug("배포 대기 목록에서 제외");
				continue;
			}
			
			log.debug("배포 대기 목록으로 이동");
			PublishedVersion publishedVer = PublishedVersion.builder()
					.document(doc)
					.versionLog(versionLog)
					.publishedVersionName(publishedVersionName)
					.build();
			
			publishedVerList.add(publishedVer);
		}
		log.debug("배포 대기 목록의 총 문서 수: " + publishedVerList.size());
		return publishedVerList;
	}
	
	// 배포 테이블에 배포 대기 문서 추가
	@Transactional
	public void registPublishedVersion(List<PublishedVersion> publishedVerList) {
		for (PublishedVersion publishedVer : publishedVerList) {
			int result = publishedVersionDAO.insert(publishedVer);
			if (result > 0) log.debug(publishedVer.getDocument().getDocumentIdx() + "번 문서 배포 테이블에 추가 성공");
			else throw new PublishedVersionException("배포판에 문서 추가 실패");
		}
	}
	
}
