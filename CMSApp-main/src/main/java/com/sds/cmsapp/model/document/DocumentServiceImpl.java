package com.sds.cmsapp.model.document;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sds.cmsapp.domain.Dept;
import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentStatus;
import com.sds.cmsapp.domain.RequestDocFilterDTO;
import com.sds.cmsapp.domain.ResponseDocCountDTO;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.Folder;
import com.sds.cmsapp.domain.MasterCode;
import com.sds.cmsapp.domain.Project;
import com.sds.cmsapp.domain.ResponseDocDTO;
import com.sds.cmsapp.domain.StatusLog;
import com.sds.cmsapp.domain.Trash;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.exception.DocumentException;
import com.sds.cmsapp.exception.DocumentVersionException;
import com.sds.cmsapp.exception.FolderException;
import com.sds.cmsapp.exception.StatusLogException;
import com.sds.cmsapp.exception.TrashException;
import com.sds.cmsapp.exception.VersionLogException;
import com.sds.cmsapp.model.statuslog.StatusLogDAO;
import com.sds.cmsapp.model.trash.TrashDAO;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {
		
	@Autowired
	private DocumentDAO documentDAO; // 전체 문서 목록 조회
	
	@Autowired
	private TrashDAO trashDAO; // 휴지통에 있는 문서 조회
	
	@Autowired
	private DocumentVersionDAO documentVersionDAO; // 버전 조회
	
	@Autowired
	private StatusLogDAO statusLogDAO; // 가장 최신 로그의 코멘트, 사원 정보 조회
	
	@Autowired
	private DocumentDetailDAO documentDetailDAO;

	
	/* 모든 문서 조회 */
	public List<Document> selectAll() {
		return documentDAO.selectAll();
	};
	
	// 전체적으로 exception 점검하기, dao의 수행 결과 null로 나오게 될 경우 어떻게 되는지 확인하기
	
	/* 결재 진행 중인 문서 목록 조회------------------------------------
	 	* Builder 패턴을 이용하여 응답 DTO에 DAO의 수행 결과 설정
	 	* 결재 상태 코드 상수를 enum으로 관리
	 ----------------------------------------------------------*/ 
	public ResponseDocCountDTO countByStatus() throws TrashException, StatusLogException {
		return ResponseDocCountDTO.builder()
				.inReviewCount(documentVersionDAO.countByStatus(DocumentStatus.IN_REVIEW.getStatusCode()))
				.reviewedCount(documentVersionDAO.countByStatus(DocumentStatus.REVIEWED.getStatusCode()))
				.publishedCount(documentVersionDAO.countByStatus(DocumentStatus.PUBLISHED.getStatusCode()))
				.rejectedCount(documentVersionDAO.countByStatus(DocumentStatus.REJECTED.getStatusCode()))
				.build();
	}
	
	/* 결재 진행 중인 문서 목록 조회------------------------------------
	 	* 필터: 날짜, 결재 상태, 프로젝트
	 	* 조건: 휴지통에 있는 문서 제외, deprecated 문서 제외
	 	* 중첩 resultMap을 지양. 두 번 타고 가는 것까지 허용함 (가독성, 유지보수성, 재귀적 문제 방지를 위해)
	 ----------------------------------------------------------*/ 
	@Transactional(readOnly = true)
	public List<ResponseDocDTO> selectFilteredList(RequestDocFilterDTO requestDTO) 
			throws TrashException, DocumentException, FolderException, StatusLogException, DocumentVersionException {
		// 응답 DTO 리스트 생성
		List<ResponseDocDTO> responseList = new ArrayList<ResponseDocDTO>(); 
		
		// 1. Document 전체 조회
		List<Document> documentList = documentDAO.selectAll(); 
		if (documentList == null) return responseList;
		for (Document doc : documentList) { // DocumentMap > Folder > Dept
			int docIdx = doc.getDocumentIdx(); // 문서 번호
			log.debug("docIdx: " + docIdx);
			
			// 2. Trash에 있는 문서 제외
			Trash trash = trashDAO.selectTypeByDocumentIdx(docIdx);
			if (trash != null) continue;
			
			// 3. Document_Version에서 레코드 조회 // DocumentVersionMap > MasterCode, VersionLog
			DocumentVersion docVer = documentVersionDAO.selectByDocumentIdx(docIdx); 
			if (docVer == null) {
				log.debug("document_version에 레코드가 없습니다.");
				continue; // 실제로 null이어서는 안 됨. 예외 처리하기
			}
			
			// 3-1. deprecated 문서 제외, 선택된 결재 상태로 필터링
			MasterCode masterCode = docVer.getMasterCode(); // DocumentVersionMap > MasterCode
			if (masterCode == null) {
				log.debug("document_version에 상태 정보가 없습니다.");
				continue; // 실제로 null이어서는 안 됨. 예외 처리하기
			}
			
			int statusCode = masterCode.getStatusCode(); // 상태 코드
			String statusName = masterCode.getStatusName(); // 상태명
			log.debug(statusName);
			if (statusCode == DocumentStatus.DEPRECATED.getStatusCode()) continue;
			if (requestDTO.getStatusList() != null && !requestDTO.getStatusList().contains(statusName)) continue;
			
			// 3-2. 버전명과 제목 조회
			VersionLog versionLog = docVer.getVersionLog();
			
			String version = versionLog.getVersion(); // 버전명
			String title = versionLog.getTitle(); // 제목
			
			// 4. Project에서 선택된 프로젝트만 조회
			Folder folder = doc.getFolder();
			
			// 4-1. 폴더명 조회
			Integer folderIdx = folder.getFolderIdx();  // DocumentMap > Folder
			String folderName = folder.getFolderName(); 
			
			// 4-2. 프로젝트명 조회
			Project project = folder.getProject(); // DocumentMap > Folder > Project
			String projectName = project.getProjectName();
			if (requestDTO.getProjectList() != null && !requestDTO.getProjectList().contains(projectName)) continue;
			
			// 5. Status_Log에서 날짜 필터링
			StatusLog statusLog = statusLogDAO.selectLatestLogByDocumentIdx(docIdx); // StatusLogtMap > Emp > Dept, StatusLogMap > MasterCode
			if (statusLog == null) log.debug("status_log에 log가 없습니다.");
			if (statusCode != statusLog.getMasterCode().getStatusCode()) {
				// Document_Version의 status와 비교 검증
				log.debug("document_version 테이블의 status_code가 status_log의 최신 status_code와 불일치합니다.");
				continue;
			}
			
			Timestamp regdate = statusLog.getRegdate(); // 상태 변경일자
			Long time = regdate.getTime();
			if ( requestDTO.getStartDate() != null && requestDTO.getEndDate() != null &&
				(time < requestDTO.getStartDate().getTime() || time > requestDTO.getEndDate().getTime())) continue;
			String stringRegdate = new SimpleDateFormat("yyyy년 M월 dd HH:mm:ss", Locale.KOREA).format(new Date(time));
			String comments = statusLog.getComments();
			
			// 5-1. 사원명 조회
			Emp emp = statusLog.getEmp(); // StatusLogMap > Emp
			String empName = emp.getEmpName(); // 상태 변경 사원
			
			// 5-2. 부서명 조회
			Dept dept = emp.getDept(); // StatusLogMap > Emp > Dept
			String deptName = dept.getDeptName();
			
			ResponseDocDTO responseDocDTO = ResponseDocDTO.builder()
					.documentIdx(docIdx) // 문서 번호
					.statusName(statusName) // 상태명
					.title(title) // 제목
					.version(version) // 버전명
					.folderIdx(folderIdx) // 폴더 번호
					.folderName(folderName) // 폴더명
					.projectName(projectName) // 프로젝트명
					.regdate(stringRegdate) // 상태 변경일자
					.comments(comments) // 상태 변경 코멘트
					.empName(empName) // 상태 변경 사원명
					.deptName(deptName) // 상태 변경 사원의 소속 부서명
					.build();
			responseList.add(responseDocDTO);
		}
		log.debug("조회된 문서 수: " + responseList.size());
		
		return responseList;
	}
	
	//새로운 문서 작성
	@Transactional
    public void documentInsert(VersionLog versionLog) throws DocumentException, VersionLogException {
	    // 문서 내용이 비어있거나 공백인지 확인
		if (!StringUtils.hasText(versionLog.getTitle()) 
			    || !StringUtils.hasText(versionLog.getContent())
			    || !StringUtils.hasText(versionLog.getComments())) {
			    throw new VersionLogException("문서 내용이 비어있습니다.");
		}

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
		documentVersion.getVersionLog().setTitle(versionLog.getTitle());
		documentVersion.getVersionLog().setContent(versionLog.getContent());
		documentVersion.getVersionLog().setComments(versionLog.getComments());
		
		int maxVersion = documentDetailDAO.findMaxVersionByDocumentIdx(versionLog.getDocument().getDocumentIdx());
		
		//버전 로그 증가
		//int versionIdx = Integer.parseInt(documentVersion.getVersionLog().getVersion());
		String newVersion = Integer.toString(maxVersion + 1);
		documentVersion.getVersionLog().setVersion(newVersion);
		
		log.debug("versionLog = "+documentVersion.getVersionLog());
		
		int result = documentDetailDAO.versionLogInsert(documentVersion.getVersionLog());
		
		if(result < 1) {
			throw new VersionLogException("문서 버전 로그 등록실패 ");
		}
		

		
		result = documentDetailDAO.documentVersionUpdate(documentVersion.getVersionLog());
		
		if(result < 1) {
			throw new DocumentException("문서 현재 버전 업데이트 실패 ");
		}
	}
	
	public void documentVersionStatusUpdate(DocumentVersion documentVersion) {
		documentDetailDAO.documentVersionStatusUpdate(documentVersion);
	}
	
	//버전관리
    public List<VersionLog> getVersionLogSelect(int documentIdx) {
        return documentDetailDAO.getVersionLogSelect(documentIdx);
    }
	
    public int documentVersionUpdate(VersionLog versionLog) {
    	return documentDetailDAO.documentVersionUpdate(versionLog);
    }
    
	@Override
	public Document fillVersionLog(final Document document) {
		DocumentVersion documentVersion = documentVersionDAO.selectByDocumentIdx(document.getDocumentIdx());
		VersionLog versionLog = documentVersion.getVersionLog();
		document.setVersionLog(versionLog);
		return document;
	}

}
