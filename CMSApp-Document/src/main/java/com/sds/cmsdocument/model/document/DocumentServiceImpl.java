package com.sds.cmsdocument.model.document;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sds.cmsdocument.domain.Dept;
import com.sds.cmsdocument.domain.DocStatus;
import com.sds.cmsdocument.domain.Document;
import com.sds.cmsdocument.domain.DocumentVersion;
import com.sds.cmsdocument.domain.Emp;
import com.sds.cmsdocument.domain.Folder;
import com.sds.cmsdocument.domain.MasterCode;
import com.sds.cmsdocument.domain.RequestDocFilterDTO;
import com.sds.cmsdocument.domain.ResponseDocDTO;
import com.sds.cmsdocument.domain.StatusLog;
import com.sds.cmsdocument.domain.VersionLog;
import com.sds.cmsdocument.exception.DocumentException;
import com.sds.cmsdocument.exception.DocumentVersionException;
import com.sds.cmsdocument.exception.EmpException;
import com.sds.cmsdocument.exception.FolderException;
import com.sds.cmsdocument.exception.StatusLogException;
import com.sds.cmsdocument.exception.TrashException;
import com.sds.cmsdocument.exception.VersionLogException;
import com.sds.cmsdocument.model.folder.FolderDAO;
import com.sds.cmsdocument.model.publishing.PublishedVersionDAO;
import com.sds.cmsdocument.model.statuslog.StatusLogDAO;
import com.sds.cmsdocument.model.trash.TrashDAO;
import com.sds.cmsdocument.model.versionlog.VersionLogDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {
		
	@Autowired
	private DocumentDAO documentDAO; // 전체 문서 목록 조회
	
	@Autowired
	private TrashDAO trashDAO; // 휴지통에 있는 문서 조회
	
	@Autowired
	private DocumentVersionDAO documentVersionDAO; // 버전, 상태 코드, 상태 변경 메시지, 상태 변경 사원, 상태 변경일자 조회
	
	@Autowired
	private VersionLogDAO versionLogDAO; // 문서 제목, 버전명 조회
	
	@Autowired
	private DocumentDetailDAO documentDetailDAO;
	
	@Autowired
	private PublishedVersionDAO publishedVersionDAO;
	
	@Autowired
	private FolderDAO folderDAO;
	
	@Autowired
	private StatusLogDAO statusLogDAO;

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
	
	/* 모든 문서 조회 */
	public List<Document> selectAll() {
		return documentDAO.selectAll();
	};
	
	/* 결재 상태별 문서 목록 수 조회 */ //결재 상태 코드 상수를 enum으로 관리 // 현재 exception이 발견될 경우, 목록이 뜨지 않음
	public Map<String, Integer> countByStatus() throws DocumentVersionException {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		Integer inReviewCount = 0;
		Integer reviewedCount = 0;
		Integer rejectedCount = 0;
		
		if (documentVersionDAO.selectCountByStatus(DocStatus.IN_REVIEW.getStatusCode()) != null) 
			inReviewCount = documentVersionDAO.selectCountByStatus(DocStatus.IN_REVIEW.getStatusCode()).getDocCount();
		if (documentVersionDAO.selectCountByStatus(DocStatus.REVIEWED.getStatusCode()) != null) 
			reviewedCount = documentVersionDAO.selectCountByStatus(DocStatus.REVIEWED.getStatusCode()).getDocCount();
		if (documentVersionDAO.selectCountByStatus(DocStatus.REJECTED.getStatusCode()) != null) 
			rejectedCount = documentVersionDAO.selectCountByStatus(DocStatus.REJECTED.getStatusCode()).getDocCount();
		
		map.put(DocStatus.IN_REVIEW.getStatusNameEn(), inReviewCount);
		map.put(DocStatus.REVIEWED.getStatusNameEn(), reviewedCount);
		map.put(DocStatus.REJECTED.getStatusNameEn(), rejectedCount);
		log.debug("map의 크기: " + map.size());
		return map;
	}
	
	/* 결재 진행 중인 문서 목록 조회----------------------------------------------------------------------
	 	* 필터: 날짜, 결재 상태, 프로젝트
	 	* 조건: 휴지통에 있는 문서 제외
	 	* 중첩 resultMap을 지양. 두 번 타고 가는 것까지 허용함 (가독성, 유지보수성, 재귀적 문제 방지를 위해)
	 	* 리미트 거는 과정이 없다. summary / detail 요청에 따른 파라미터 유효성 검증
	  --------------------------------------------------------------------------------------------*/
	@Transactional(readOnly = true)
	public List<ResponseDocDTO> getFilteredList(RequestDocFilterDTO requestDTO) 
			throws DocumentException, TrashException, DocumentVersionException, VersionLogException, EmpException {
		List<ResponseDocDTO> responseList = new ArrayList<ResponseDocDTO>(); // 응답 DTO를 담을 리스트 생성
		List<Document> errorDocList = new ArrayList<Document>(); // 문서 조회 시 에러가 발생한 문서를 담을 리스트 생성
		
		// 문서 목록 전체 조회 // 문서가 0개 있을 경우 빈 리스트가 반환됨
		List<Document> documentList = documentDAO.selectAll();			// DocumentDAO 수행 결과: DocumentMap > FolderMap > ProjectMap

		// 문서 정보 조회 시작
		for (Document doc : documentList) {
			// 폴더 정보와 프로젝트 정보를 가지고 있는 Document 한 건 조회
			int docIdx = doc.getDocumentIdx(); // 문서 번호 // 문서 번호가 null일 경우의 수는 없으므로 예외 처리 생략
			Folder folder = doc.getFolder();
			if (folder == null) throw new FolderException("[java.lang.NullPointerErrorException] document 테이블에서 폴더 정보를 찾을 수 없습니다.");
			
			Integer folderIdx = folder.getFolderIdx(); // 폴더 번호
			String folderName = folder.getFolderName();  // 폴더명
			// folder table의 project_idx는 not null이기 때문에 null 값이 들어올 가능성은 없지만 예외 처리
			// throw new FolderException("[java.lang.NullPointerErrorException] document 테이블에서 프로젝트 정보를 찾을 수 없습니다.");
			if (folder.getProject() == null) throw new FolderException("[java.lang.NullPointerErrorException] document 테이블에서 프로젝트 정보를 찾을 수 없습니다.");
			String projectName = folder.getProject().getProjectName();
			//String projectName =  Optional.ofNullable(folder.getProject()).map(Project::getProjectName).orElse("N/A");
			
			// 프로젝트 이름과 폴더 이름이 같으면 폴더 이름 생략
			if (projectName.equals(folderName)) folderName = "";
			
			// 문서결재 상태, 버전 정보, 상태 변경 사원, 상태 변경 코멘트, 상태 변경 메시지를 가지고 있는 DocumentVersion 한 건 조회
			DocumentVersion docVer = documentVersionDAO.selectByDocumentIdx(docIdx); // DocumentVersionDAO 수행 결과: DocumentVersionMap > MasterCode, Emp, VersionLog
			if (docVer == null)	throw new DocumentVersionException("document_version 테이블에서 해당 문서를 찾을 수 없습니다.");
			
			if (docVer.getMasterCode() == null)  throw new DocumentVersionException("document_version에서 결재 상태 정보를 찾을 수 없습니다.");
			String statusName = docVer.getMasterCode().getStatusName(); // 상태명
			String statusComments = docVer.getStatusComments(); // 상태 변경 코멘트
			Timestamp statusRegdate = docVer.getStatusRegdate(); // 상태 변경일자
			Long timeRegdate = statusRegdate.getTime(); // 비교 가능한 long 타입으로 변환
			String stringRegdate = new SimpleDateFormat("yyyy년 M월 dd일 HH:mm:ss", Locale.KOREA).format(new Date(timeRegdate)); // 문자열 포맷팅
			
			Emp emp = docVer.getEmp();
			if (emp == null) throw new DocumentVersionException("document_version에서 사원 정보를 찾을 수 없습니다.");
			String empName = emp.getEmpName(); // 상태 변경 사원명
			
			Dept dept = emp.getDept();
			String deptName =  Optional.ofNullable(dept).map(Dept::getDeptName).orElse("N/A");
			
			VersionLog selectedVersionLog = versionLogDAO.select(docVer.getVersionLog().getVersionLogIdx()); // VersionLogDAO 수행 결과: VersionLog
			if (selectedVersionLog == null) throw new DocumentVersionException("document_Version 테이블에서 버전 정보를 찾을 수 없습니다.");
			String version = selectedVersionLog.getVersion(); // 버전명
			String title = selectedVersionLog.getTitle(); // 제목
			
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
			 --------------------------------------------------------------------------------------------*/
			if (requestDTO.getStatusList() != null && !requestDTO.getStatusList().contains(statusName)) continue;
			
			/* 필터 2번) 프로젝트 ---------------------------------------------------------------------------
		 	--------------------------------------------------------------------------------------------*/
			if (requestDTO.getProjectList() != null && !requestDTO.getProjectList().contains(projectName)) continue;
			
			/* 필터 3번) 상태 변경일 ---------------------------------------------------------------------------
	 		--------------------------------------------------------------------------------------------*/
			if ( requestDTO.getStartDate() != null && requestDTO.getEndDate() != null &&
					(timeRegdate < requestDTO.getStartDate().getTime() || timeRegdate > requestDTO.getEndDate().getTime())) {
				continue;
			}
			responseList.add(responseDocDTO);
		}
		// 조회된 문서 수 출력
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
		documentVersion.setEmp(new Emp(1)); // 임시
		documentVersion.setStatusComments("새로 생성된 문서"); // 임시 코멘트
		
		//log.debug("document_version is " + documentVersion);
		
		result = documentDAO.documentVersionInsert(documentVersion);
		
		if(result < 1) {
			throw new DocumentException("문서 현재 버전 insert 실패 ");
		}
		
		StatusLog statusLog = new StatusLog(documentVersion.getDocument().getDocumentIdx(), 
				documentVersion.getVersionLog().getVersionLogIdx(), documentVersion.getEmp().getEmpIdx(), 100,  
				documentVersion.getStatusComments());
		statusLogDAO.insert(statusLog);

		if (result < 1) {
			throw new StatusLogException("상태 변결 로그 insert 실패");
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
		DocumentVersion documentVersion = new DocumentVersion();
		documentVersion.setDocumentVersionIdx(documentIdx);
		MasterCode masterCode = new MasterCode();
		
		
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
