package com.sds.cmsdocument.dashboard.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sds.cmsdocument.domain.DocStatus;
import com.sds.cmsdocument.domain.Document;
import com.sds.cmsdocument.domain.DocumentVersion;
import com.sds.cmsdocument.domain.Emp;
import com.sds.cmsdocument.domain.ErrorResponse;
import com.sds.cmsdocument.domain.MasterCode;
import com.sds.cmsdocument.domain.Project;
import com.sds.cmsdocument.domain.RequestDocFilterDTO;
import com.sds.cmsdocument.domain.RequestDocStatusChanging;
import com.sds.cmsdocument.domain.ResponseDocDTO;
import com.sds.cmsdocument.exception.DocumentException;
import com.sds.cmsdocument.exception.DocumentVersionException;
import com.sds.cmsdocument.exception.FolderException;
import com.sds.cmsdocument.exception.StatusLogException;
import com.sds.cmsdocument.model.document.DocumentService;
import com.sds.cmsdocument.model.document.DocumentVersionService;
import com.sds.cmsdocument.model.mastercode.MasterCodeService;
import com.sds.cmsdocument.model.project.ProjectService;
import com.sds.cmsdocument.model.updating.MainUpdatingStatusService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller(value="dashboardRestDocumentController")
public class RestDocumentController {
	
	@Autowired
	DocumentService documentService;
	
	@Autowired
	ProjectService projectService; // 필터의 프로젝트 목록 조회
	
	@Autowired
	MasterCodeService masterCodeService; // 필터의 결재 상태 목록 조회
	
	@Autowired
	DocumentVersionService documentVersionService; // 문서의 현재 상태 정보 검증
	
	@Autowired
	MainUpdatingStatusService mainUpdatingStatusService; // 문서 상태 업데이트
	
	/*############################# GET MAPPING #############################*/
	
	 // 결재 진행 상태별 문서 수 조회 (휴지통에 있는 문서 제외) 
	@GetMapping("/dashboard/summary/count")
	public ResponseEntity<Map<String, Integer>> getCountByStatus() {
		return ResponseEntity.ok(documentService.countByStatus());
	}
	
	 // 필터에 따라 결재 진행 중인 문서 목록 조회 (휴지통에 있는 문서 제외)
	@GetMapping("/dashboard/list/entire") 
	public ResponseEntity<List<ResponseDocDTO>> getFilteredList(RequestDocFilterDTO filterDTO) {
		log.debug("입력받은 상태 코드: " + filterDTO.getStatusList());
		return ResponseEntity.ok(documentService.getFilteredList(filterDTO));
	}
	
	 // 문서 조회 시 필터에 사용되는 전체 프로젝트 목록 조회
	@GetMapping("/admin/project/list")
	public ResponseEntity<List<Project>> getProjectList() {
		return ResponseEntity.ok(projectService.selectAll());
	}
	
	 // 문서 조회 시 필터에 사용되는 전체 상태 목록 조회
	@GetMapping("/admin/status/list")
	public ResponseEntity<List<MasterCode>> getStatusList() {
		return ResponseEntity.ok(masterCodeService.selectAll());
	}
	
	/*############################# POST MAPPING #############################*/
	
	// 리뷰 중 문서에 대하여 [리뷰 완료] 버튼 클릭 (200  -> 300)
	@PostMapping("/admin/document/{documentIdx}/status/reviewed")
	public ResponseEntity<?> changeStatusToReviewed(
			@PathVariable(value="documentIdx") int documentIdx, 
			RequestDocStatusChanging requestDTO) {
		
		// review 권한이 있는 사원인지(admin 역할) 검증
		if (!requestDTO.getRoleName().equals("Admin")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("권한이 없는 사용자입니다."));
		
		// 사원 번호 가져오기
		Emp emp = new Emp(requestDTO.getEmpIdx());
		
		// 현재 문서 상태가 '리뷰 중' (200)인 문서인지 검증
		MasterCode masterCode = documentVersionService.selectByDocumentIdx(documentIdx).getMasterCode();
		if (masterCode != null) {
			log.debug("문서 상태 조회 성공");
			if (masterCode.getStatusCode() != 200) {
				log.debug("잘못된 요청입니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("잘못된 요청입니다."));
			}
		} else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("문서 정보를 불러오는 데 실패했습니다."));
		
		// tobe 상태
		MasterCode tobe = new MasterCode(DocStatus.REVIEWED.getStatusCode());
		// DocumentVersion의 상태 업데이트 + status_log(독립 테이블)에 로그 저장
		DocumentVersion documentVersion = new DocumentVersion(new Document(documentIdx), tobe, emp, requestDTO.getComments());
		mainUpdatingStatusService.changeStatusOne(documentVersion);
		
		return ResponseEntity.ok().build();
	}
	
	// 리뷰 중 문서에 대하여 [반려] 버튼 클릭 (200 -> 500)
	@PostMapping("/admin/document/{documentIdx}/status/rejected")
	public ResponseEntity<?> changeStatusToRejected(@PathVariable(value="documentIdx") int documentIdx, RequestDocStatusChanging requestDTO) {
		// review 권한이 있는 사원인지(admin 역할) 검증
		if (!requestDTO.getRoleName().equals("Admin")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("권한이 없는 사용자입니다."));
		
		// 사원 번호 가져오기
		Emp emp = new Emp(requestDTO.getEmpIdx());
		
		// 현재 문서 상태가 '리뷰 중' (200)인 문서인지 검증
		MasterCode masterCode = documentVersionService.selectByDocumentIdx(documentIdx).getMasterCode();
		if (masterCode != null) {
			if (masterCode.getStatusCode() != 200) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("잘못된 요청입니다."));
			}
		} else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("문서 정보를 불러오는 데 실패했습니다."));
		
		// tobe 상태
		MasterCode tobe = new MasterCode(DocStatus.REJECTED.getStatusCode());
		// DocumentVersion의 상태 업데이트 + status_log(독립 테이블)에 로그 저장
		DocumentVersion documentVersion = new DocumentVersion(new Document(documentIdx), tobe, emp, requestDTO.getComments());
		mainUpdatingStatusService.changeStatusOne(documentVersion);
		
		return ResponseEntity.ok().build();
	}
	
	// 반려 문서에 대하여 [확인] 버튼 클릭 (500 -> 100), 버전 되돌리기
	@PostMapping("/admin/document/{documentIdx}/status/confirm")
	public ResponseEntity<?> changeStatusBackToDraft(@PathVariable(value="documentIdx") int documentIdx, RequestDocStatusChanging requestDTO) {

		// 사원 번호 가져오기
		Emp emp = new Emp(requestDTO.getEmpIdx());
		
		// 해당 프로젝트에 접근할 수 있는 사원인지 검증
		
		// 현재 문서 상태가 '반려' (500)인 문서인지 검증
		MasterCode masterCode = documentVersionService.selectByDocumentIdx(documentIdx).getMasterCode();
		if (masterCode != null) {
			if (masterCode.getStatusCode() != 500) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("잘못된 요청입니다."));
		} else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("문서 정보를 불러오는 데 실패했습니다."));
		
		// tobe 상태
		MasterCode tobe = new MasterCode(DocStatus.DRAFT.getStatusCode());
		// DocumentVersion의 상태 업데이트 + status_log(독립 테이블)에 로그 저장
		DocumentVersion documentVersion = new DocumentVersion(new Document(documentIdx), tobe, emp, requestDTO.getComments());
		mainUpdatingStatusService.changeStatusRejectedToDraft(documentVersion);
		
		return ResponseEntity.ok().build();
	}
	
	@ExceptionHandler({DocumentException.class, FolderException.class, DocumentVersionException.class, StatusLogException.class})
	public ResponseEntity<String> handle(RuntimeException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}


}
