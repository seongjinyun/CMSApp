package com.sds.cmsapp.model.document;

import java.util.ArrayList;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sds.cmsapp.domain.DocStatus;
import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.RequestDocFilterDTO;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.Folder;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.ResponseDocDTO;
import com.sds.cmsapp.domain.StatusLog;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.exception.DocumentException;
import com.sds.cmsapp.exception.DocumentVersionException;
import com.sds.cmsapp.exception.EmpException;
import com.sds.cmsapp.exception.FolderException;
import com.sds.cmsapp.exception.StatusLogException;
import com.sds.cmsapp.exception.TrashException;
import com.sds.cmsapp.exception.VersionLogException;
import com.sds.cmsapp.model.folder.FolderDAO;
import com.sds.cmsapp.model.publishing.PublishedVersionDAO;
import com.sds.cmsapp.model.statuslog.StatusLogDAO;
import com.sds.cmsapp.model.trash.TrashDAO;
import com.sds.cmsapp.model.versionlog.VersionLogDAO;

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
	private VersionLogDAO versionLogDAO; // 문서 제목, 버전명 조회
	
	@Autowired
	private DocumentDetailDAO documentDetailDAO;
	
	@Autowired
	private PublishedVersionDAO publishedVersionDAO;
	
	@Autowired
	private FolderDAO folderDAO;

	@Override
	public Document select(int documentIdx) {
		return documentDAO.select(documentIdx);
	}
	
	// 모든 문서 조회
	public List<DocumentVersion> selectAllOrigin() {
		List<DocumentVersion> resultList = new ArrayList<>();
		List<Folder> topFolderList = folderDAO.selectTopFolder();
		for(Folder folder : topFolderList) {
			Map<String, Integer> map = new HashMap<>();
			map.put("folderIdx", folder.getFolderIdx());
			resultList.addAll(documentListSelect(map));
		}		
		return resultList;
	};
	
	public List<Document> selectAllByRange(final Map<String, Integer> map){
		return documentDAO.selectAllByRange(map);
	}
	
	public List selectAllForDashboard(Map map) {
		return documentDAO.selectAllForDashboard(map);
	};
	/* 모든 문서 조회 */
	public List<Document> selectAll() {
		return documentDAO.selectAll();
	};
	
////////////////////////////// 전체적으로 exception 점검하기, dao의 수행 결과 null로 나오게 될 경우 어떻게 되는지 확인하기
	
	/* 결재 상태별 문서 목록 수 조회------------------------------------
	 	* Builder 패턴을 이용하여 응답 DTO에 DAO의 수행 결과 설정
	 	* 결재 상태 코드 상수를 enum으로 관리
	 ----------------------------------------------------------*/ 
	public Map<String, Integer> countByStatus() throws DocumentVersionException {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		Integer inReviewCount = 0;
		Integer reviewedCount = 0;
		Integer rejectedCount = 0;
		Integer publishedCount = 0;
		
		if (documentVersionDAO.selectCountByStatus(DocStatus.IN_REVIEW.getStatusCode()) != null) 
			inReviewCount = documentVersionDAO.selectCountByStatus(DocStatus.IN_REVIEW.getStatusCode()).getDocCount();
		if (documentVersionDAO.selectCountByStatus(DocStatus.REVIEWED.getStatusCode()) != null) 
			reviewedCount = documentVersionDAO.selectCountByStatus(DocStatus.REVIEWED.getStatusCode()).getDocCount();
		if (documentVersionDAO.selectCountByStatus(DocStatus.REJECTED.getStatusCode()) != null) 
			rejectedCount = documentVersionDAO.selectCountByStatus(DocStatus.REJECTED.getStatusCode()).getDocCount();
		if (documentVersionDAO.selectCountByStatus(DocStatus.PUBLISHED.getStatusCode()) != null) 
			publishedCount = documentVersionDAO.selectCountByStatus(DocStatus.PUBLISHED.getStatusCode()).getDocCount();
		
		map.put(DocStatus.IN_REVIEW.getStatusNameEn(), inReviewCount);
		map.put(DocStatus.REVIEWED.getStatusNameEn(), reviewedCount);
		map.put(DocStatus.REJECTED.getStatusNameEn(), rejectedCount);
		map.put(DocStatus.PUBLISHED.getStatusNameEn(), publishedCount);
		log.debug("map의 크기: " + map.size());
		return map;
	}
	
	/* 결재 진행 중인 문서 목록 조회----------------------------------------------------------------------
	 	* 필터: 날짜, 결재 상태, 프로젝트
	 	* 조건: 휴지통에 있는 문서 제외
	 	* 중첩 resultMap을 지양. 두 번 타고 가는 것까지 허용함 (가독성, 유지보수성, 재귀적 문제 방지를 위해)
	 	* 남아 있는 문제: 상태 변경 일자 순으로 정렬하는 기능이 없다. (documentVersion 테이블에 regdate 추가하기?)
	 	* 리미트 거는 과정이 없다. summary / detail 요청에 따른 파라미터 유효성 검증
	  --------------------------------------------------------------------------------------------*/
	@Transactional(readOnly = true)
	public List<ResponseDocDTO> getFilteredList(RequestDocFilterDTO requestDTO) 
			throws DocumentException, TrashException, DocumentVersionException, StatusLogException, EmpException {
		List<ResponseDocDTO> responseList = new ArrayList<ResponseDocDTO>(); // 응답 DTO 리스트 생성
		
		// 문서 목록 전체 조회
		List<Document> documentList = documentDAO.selectAll();			// DocumentDAO 수행 결과: DocumentMap > FolderMap > ProjectMap
		if (documentList == null) return responseList; 					// 문서 목록이 비어 있을 경우 비어 있는 응답 리스트를 반환
		 
		// 문서 정보 조회 시작
		for (Document doc : documentList) {
			// 폴더 정보와 프로젝트 정보를 가지고 있는 Document 한 건 조회
			int docIdx = doc.getDocumentIdx(); // 문서 번호
			Folder folder = doc.getFolder();
			if (folder == null) throw new FolderException("document 테이블에서 폴더 정보를 찾을 수 없습니다.");
			Integer folderIdx = folder.getFolderIdx(); // 폴더 번호
			String folderName = folder.getFolderName();  // 폴더명
			if (folder.getProject() == null) throw new FolderException("document 테이블에서 프로젝트 정보를 찾을 수 없습니다.");
			String projectName = doc.getFolder().getProject().getProjectName(); // 프로젝트명
			
			// 문서결재 상태와 버전 정보를 가지고 있는 DocumentVersion 한 건 조회
			DocumentVersion docVer = documentVersionDAO.selectByDocumentIdx(docIdx); // DocumentVersionDAO 수행 결과: DocumentVersionMap > MasterCode, VersionLog
			if (docVer == null)	throw new DocumentVersionException("document_version 테이블에서 해당 문서를 찾을 수 없습니다.");
			if (docVer.getMasterCode() == null) throw new DocumentVersionException("document_version에서 결재 상태 정보를 찾을 수 없습니다.");
			int statusCode = docVer.getMasterCode().getStatusCode(); // 상태 코드
			String statusName = docVer.getMasterCode().getStatusName(); // 상태명
			
			VersionLog selectedVersionLog = versionLogDAO.select(docVer.getVersionLog().getVersionLogIdx()); // VersionLogDAO 수행 결과: VersionLog
			if (selectedVersionLog == null)	throw new DocumentVersionException("document_Version 테이블에서 버전 정보를 찾을 수 없습니다.");
			String version = selectedVersionLog.getVersion(); // 버전명
			String title = selectedVersionLog.getTitle(); // 제목
			
			// 상태 변경 정보를 가지고 있는 StatusLog 한 건 조회
			StatusLog statusLog = statusLogDAO.selectLatestLogByDocumentIdx(docIdx); // StatusLogDAO 수행 결과: EmpMap > Dept / MasterCode
			if (statusLog == null) throw new StatusLogException("status_log 테이블에서 해당 문서를 찾을 수 없습니다.");
			if (statusLog.getMasterCode().getStatusCode() != statusCode) throw new StatusLogException("document_version 테이블의 status_code가 status_log의 최신 status_code와 불일치합니다.");
			String statusComments = statusLog.getComments();
			Timestamp regdate = statusLog.getRegdate(); 
			Long timeRegdate = regdate.getTime();
			String stringRegdate = new SimpleDateFormat("yyyy년 M월 dd HH:mm:ss", Locale.KOREA).format(new Date(timeRegdate));
			Emp emp = statusLog.getEmp(); 
			if (emp == null) throw new StatusLogException("status_log 테이블에서 사원 정보를 찾을 수 없습니다.");
			//int empIdx = emp.getEmpIdx(); // 상태 변경 사원 번호
			String empName = emp.getEmpName(); // 상태 변경 사원명
			if (emp.getDept() == null) throw new EmpException("status_log 테이블에서 부서 정보를 찾을 수 없습니다.");
			//int deptIdx = emp.getDept().getDeptIdx(); // 상태 변경 사원의 부서 번호
			String deptName = emp.getDept().getDeptName(); // 상태 변경 사원의 부서명
						
			log.debug(new StringBuilder()
					.append("문서 번호: " + docIdx)
					.append(", 프로젝트명: " + projectName)
					.append(", 폴더명: " + folderName)
					.append(", 버전명: " + version)
					.append(", 제목: " + title)
					.append("\n상태 코드: " + statusCode)
					.append(", 상태명: " + statusName)
					.append(", 상태 변경 코멘트: " + statusComments)
					.append(", 상태 변경일자: " + stringRegdate)
					.append("\n부서명: " + deptName)
					.append(", 사원명: " + empName)
					.toString());
			
			ResponseDocDTO responseDocDTO = ResponseDocDTO.builder()
					.documentIdx(docIdx) // 문서 번호
					.statusName(statusName) // 상태명
					.title(title) // 제목
					.version(version) // 버전명
					.folderIdx(folderIdx) // 폴더 번호
					.folderName(folderName) // 폴더명
					.projectName(projectName) // 프로젝트명
					.regdate(stringRegdate) // 상태 변경일자
					.comments(statusComments) // 상태 변경 코멘트
					.empName(empName) // 상태 변경 사원명
					.deptName(deptName) // 상태 변경 사원의 소속 부서명
					.build();
					
			// 문서 정보 필터링 시작
			// 휴지통에 있는 문서는 응답 리스트에서 제외 (생략 가능?)
			if (trashDAO.selectTypeByDocumentIdx(docIdx) != null) continue; // TrashDAO 수행 결과: Trash
			
			/* 필터 1번) 결재 상태 ---------------------------------------------------------------------------
			 	* 문서 목록 중 필터에서 선택된 결재 상태에 있지 않은 문서는 응답 리스트에서 제외
			 	* '전체 보기'를 선택한 경우(requestDTO.getStatusList() == null) 문서를 응답 리스트에 포함
			 	* 검토 중
			 		* deprecated 상태인 문서를 포함시킬지 말지
			 		* '직접 선택'을 클릭한 후 아무것도 선택하지 않았을 때의 처리 (현재 null이 아닌 값으로 들어와서 문제는 없음)
			 --------------------------------------------------------------------------------------------*/
			if (requestDTO.getStatusList() != null 
					&& !requestDTO.getStatusList().contains(statusName)) {
				log.debug("결재 상태 코드" + statusCode + ", 결재 상태명: " + statusName + "는 선택되지 않았습니다.");
				continue;
			}
			
			/* 필터 2번) 프로젝트 ---------------------------------------------------------------------------
		 		* 문서 목록 중 필터에서 선택된 프로젝트에 있지 않은 문서는 응답 리스트에서 제외
		 		* '전체 보기'를 선택한 경우(requestDTO.getProjectList() == null) 문서를 응답 리스트에 포함
		 		* 검토 중
			 		* '직접 선택'을 클릭한 후 아무것도 선택하지 않았을 때의 처리 (현재 null이 아닌 값으로 들어와서 문제는 없음)
		 	--------------------------------------------------------------------------------------------*/
			if (requestDTO.getProjectList() != null 
					&& !requestDTO.getProjectList().contains(projectName)) {
				log.debug("프로젝트명: " + projectName + ", 폴더명: " + folderName + ", 문서 번호: " + docIdx + "는 선택되지 않았습니다.");
				continue;
			}
			
			/* 필터 3번) 상태 변경일 ---------------------------------------------------------------------------
	 			* 문서 목록 중 필터에서 선택된 기간에 최신 상태 로그가 등록되지 않은 문서는 응답 리스트에서 제외
	 			* '전체 보기'를 선택한 경우(requestDTO.getStartDate() == null, requestDTO.getEndDate() == null) 문서를 응답 리스트에 포함
	 			* 검토 중
		 		* '직접 선택'을 클릭한 후 아무것도 선택하지 않았을 때의 처리 (현재 null이 아닌 값으로 들어와서 문제는 없음)
	 		--------------------------------------------------------------------------------------------*/
			if ( requestDTO.getStartDate() != null && requestDTO.getEndDate() != null &&
					(timeRegdate < requestDTO.getStartDate().getTime() || timeRegdate > requestDTO.getEndDate().getTime())) {
				log.debug("해당 문서의 최신 상태 로그는 사용자가 입력한 기간에 등록되지 않았습니다.");
				continue;
			}
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
	public List<DocumentVersion> documentListSelect(final Map<String, Integer> map) {
		List<DocumentVersion> resultList = documentDAO.documentListSelect(map);
		List<Integer> documentIdxInTrashList = trashDAO.selectDocumentIdx();

		for(int i = 0; i < resultList.size(); i++) {
			DocumentVersion dto = resultList.get(i);
			if(documentIdxInTrashList.contains(dto.getDocument().getDocumentIdx())){
				resultList.remove(i);
			}
		}
		return resultList;
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
	
	@Override
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
	
	@Override
	public boolean isPublished(final int doucmentIdx) {
		boolean flag = false;
		if(publishedVersionDAO.selectByDocumentIdx(doucmentIdx) != null) {
			flag = true;
		}
		return flag;
	}
	
	@Override
	public int update(final Document document) {
		return documentDAO.update(document);
	};
	
	@Override
	public void versionLogDelete(int versionLog) {
		documentDetailDAO.versionLogDelete(versionLog);
	}
}
