package com.sds.cmsapp.model.document;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.RequestDocumentDTO;
import com.sds.cmsapp.domain.ResponseDocumentCountDTO;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.FilterItemDTO;
import com.sds.cmsapp.domain.ResponseDocumentDTO;
import com.sds.cmsapp.domain.StatusLog;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.exception.DocumentException;
import com.sds.cmsapp.exception.DocumentVersionException;
import com.sds.cmsapp.exception.FolderException;
import com.sds.cmsapp.exception.ProjectException;
import com.sds.cmsapp.exception.StatusLogException;
import com.sds.cmsapp.exception.TrashException;
import com.sds.cmsapp.exception.VersionLogException;
import com.sds.cmsapp.model.folder.FolderDAO;
import com.sds.cmsapp.model.statuslog.StatusLogDAO;
import com.sds.cmsapp.model.trash.TrashDAO;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {
		
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private FolderDAO folderDAO;
	
	@Autowired
	private StatusLogDAO statusLogDAO;
	
	@Autowired
	private DocumentVersionDAO documentVersionDAO;
	
	/* 결재 상태별 문서 수 조회 */
	public ResponseDocumentCountDTO countByStatus()
		throws TrashException, StatusLogException {
		
		// DAO에 보낼 파라미터 DTO 설정
		int inReview = 200;
		int reviewed =  300;
		int published = 400;
		int rejected = 500;
		
		// 요청 DTO를 DAO에 전달하여 필터가 적용된 문서 목록 조회하고, 그 결과를 응답 DTO에 설정
		return ResponseDocumentCountDTO.builder()
				.inReview(statusLogDAO.countByStatus(inReview))
				.reviewed(statusLogDAO.countByStatus(reviewed))
				.published(statusLogDAO.countByStatus(published))
				.rejected(statusLogDAO.countByStatus(rejected))
				.build();
	};
	
	/* 결재 상태별로 문서 목록 조회 (10개만, 휴지통에 있는 문서 제외) */
	public List<ResponseDocumentDTO> selectSummaryListOfCurrentStatus(int statusCode) {
		
		// 브라우저에 보낼 DTO 리스트 생성
		List<ResponseDocumentDTO> responseList = new ArrayList<ResponseDocumentDTO>();
		
		// DAO에 일 시키기
		List<StatusLog> resultDocumentList = statusLogDAO.selectSummaryListOfLatestRegisteredLog(statusCode);
		
		// 응답 DTO 리스트에 응답 DTO 추가
		for (StatusLog s : resultDocumentList) {
			int documentIdx = s.getDocument().getDocumentIdx(); // 문서 idx

			ResponseDocumentDTO responseDocumentDTO = ResponseDocumentDTO.builder()
					.documentIdx(documentIdx)
					.comments(s.getComments())
					.statusCode(s.getMasterCode().getStatusCode())
					.statusName(s.getMasterCode().getStatusName()) // MasterCodeMap
					.regdate(new SimpleDateFormat("yyyy년 M월 dd일 HH:mm:ss").format(new Date(s.getRegdate().getTime())))
					.empName(s.getEmp().getEmpName()) // EmpMap
					.deptName(s.getEmp().getDept().getDeptName())
					.folderName(s.getDocument().getFolder().getFolderName()) // DocumentMap
					.projectName(s.getDocument().getFolder().getProject().getProjectName()) // DocumentMap, FolderMap
					.title(documentVersionDAO.selectByDocumentIdx(documentIdx).getVersionLog().getTitle()) // VersionLogMap
					.version(documentVersionDAO.selectByDocumentIdx(documentIdx).getVersionLog().getVersion()) // VeresionLogMap
					.build();
			
			responseList.add(responseDocumentDTO);
			log.debug("응답 목록에 문서를 하나 추가하였습니다.");
		}
		
		return responseList;
	}
	
	/* 필터를 적용하여 결재 진행 중인 문서 목록 조회 (휴지통에 있는 문서 제외) */
	public List<ResponseDocumentDTO> selectFilteredListOfCurrentStatus(RequestDocumentDTO requestDTO) 
			throws TrashException, DocumentException, FolderException, StatusLogException, DocumentVersionException {
		
		// 브라우저에 보낼 DTO 리스트 생성
		List<ResponseDocumentDTO> responseList = new ArrayList<ResponseDocumentDTO>();
		
		// DAO에 보낼 파라미터 DTO 설정
		List<Integer> folderIdxListInProject = folderDAO.selectFolderIdxListInProject(requestDTO);
		log.debug("선택된 프로젝트에 속한 폴더 idx 목록: " + folderIdxListInProject);
		List<Integer> documentIdxListInProject = documentDAO.selectDocumentIdxListInFolder(folderIdxListInProject);
		log.debug("선택된 프로젝트에 속한 폴더에 속한 문서 idx 목록: " + documentIdxListInProject);
		
		FilterItemDTO filterItem = FilterItemDTO.builder()
				.statusCodeList(requestDTO.getStatusCodeList()) // 상태 코드
				.startDate(requestDTO.getStartDate()) // 시작 일자
				.endDate(requestDTO.getEndDate()) // 마지막 일자
				.documentIdxListInProject(documentIdxListInProject) // 프로젝트에 속한 문서 번호
				.build();
		log.debug("StatusLogDAO에게 보낼 파라미터 DTO 생성이 완료되었습니다.");
		
		// 요청 DTO를 DAO에 전달하여 필터가 적용된 문서 목록 조회
		List<StatusLog> resultDocumentList = statusLogDAO.selectFilteredListOfLatestRegisteredLog(filterItem); // DAO에 요청 파라미터를 담은 DTO를 전달하여 조건에 따른 문서 목록 조회
		log.debug("조회된 문서의 수: " + resultDocumentList.size());
		if (resultDocumentList.size() == 0) return responseList; 	// 문서가 0개 조회될 경우 바로 리턴
	
		// 응답 DTO 리스트에 응답 DTO 추가
		for (StatusLog s : resultDocumentList) {
			int documentIdx = s.getDocument().getDocumentIdx(); // 문서 idx

			ResponseDocumentDTO responseDocumentDTO = ResponseDocumentDTO.builder()
					.documentIdx(documentIdx)
					.comments(s.getComments())
					.statusCode(s.getMasterCode().getStatusCode())
					.statusName(s.getMasterCode().getStatusName()) // MasterCodeMap
					.regdate(new SimpleDateFormat("yyyy년 M월 dd일 HH:mm:ss").format(new Date(s.getRegdate().getTime())))
					.empName(s.getEmp().getEmpName()) // EmpMap
					.deptName(s.getEmp().getDept().getDeptName())
					.folderName(s.getDocument().getFolder().getFolderName()) // DocumentMap
					.projectName(s.getDocument().getFolder().getProject().getProjectName()) // DocumentMap, FolderMap
					.title(documentVersionDAO.selectByDocumentIdx(documentIdx).getVersionLog().getTitle()) // VersionLogMap
					.version(documentVersionDAO.selectByDocumentIdx(documentIdx).getVersionLog().getVersion()) // VeresionLogMap
					.build();
			
			responseList.add(responseDocumentDTO);
			log.debug("응답 목록에 문서를 하나 추가하였습니다.");
		}
		
		return responseList;
	}

	// 선택 문서 조회
	public Document select(int documentIdx) {
		return documentDAO.select(documentIdx);
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
	
	//document/detail 문서 상세보기 
	public DocumentVersion documentDetailSelect(DocumentVersion documentVersion) {
		return documentDAO.documentDetailSelect(documentVersion);
	}
	
	@Override // 임시로 만들어뒀습니다 -박준형
	public int delete(int documentIdx) {
		return documentDAO.delete(documentIdx);
	}
	
	@Override // 박준형 추가
	public List<Document> selectByFolderIdx(int folder_idx) {
		return documentDAO.selectByFolderIdx(folder_idx);
	}

	// 폴더 idx 목록에 따른 문서 idx 목록 조회
	@Override
	public List<Integer> selectDocumentIdxListInFolder(List<Integer> folderIdxList) {
		return documentDAO.selectDocumentIdxListInFolder(folderIdxList);
	}

	@Override
	public Document selectMap(int documentIdx) {
		return documentDAO.selectMap(documentIdx);
	}

}
