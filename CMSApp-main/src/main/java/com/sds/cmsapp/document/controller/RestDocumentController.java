package com.sds.cmsapp.document.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentRequest;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.Folder;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.exception.DocumentException;
import com.sds.cmsapp.exception.FolderException;
import com.sds.cmsapp.exception.VersionLogException;
import com.sds.cmsapp.model.document.DocumentService;
import com.sds.cmsapp.model.document.DocumentVersionService;
import com.sds.cmsapp.model.folder.FolderService;
import com.sds.cmsapp.model.trash.TrashService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestDocumentController {

	@Autowired
	private DocumentService documentService;

	@Autowired
	private FolderService folderService;
	
	@Autowired
	private TrashService trashService;
	
	@Autowired
	private DocumentVersionService documentVersionService;
	

	@PostMapping("/document/save")
	public ResponseEntity createDocument(@ModelAttribute DocumentRequest documentRequest) {
		Document document = documentRequest.getDocument();
		VersionLog versionLog = documentRequest.getVersionLog();

		log.debug("document 안의 folderIdx " + document.getFolder().getFolderIdx());
		log.debug("document 안의 empIdx is " + document.getEmp().getEmpIdx());
		log.debug("version log title is " + versionLog.getTitle());
		log.debug("version log content is " + versionLog.getContent());

		versionLog.setDocument(document);

		documentService.documentInsert(versionLog);

		ResponseEntity entity = ResponseEntity.ok("DB 입력 성공");
		return entity;
    }
	
	//수정 글 저장
	@PostMapping("/document/edit")
	public ResponseEntity<String> editDocument(@ModelAttribute DocumentRequest documentRequest) {
		VersionLog versionLog = documentRequest.getVersionLog();
		
		documentService.versionUpdate(versionLog);
		ResponseEntity<String> entity = ResponseEntity.ok("DB입력 성공");
		return entity;
	}
	
	@GetMapping("/document/folder/list")
	public ResponseEntity getFolderList() throws FolderException {
		List<Folder> folderList = folderService.selectTopFolder();
		for(int i = 0; i < folderList.size(); i++) {
			Folder folder = folderList.get(i);
			folder = folderService.completeFolder(folder.getFolderIdx());
			folderList.set(i, folder);
		}
		return new ResponseEntity<>(folderList, HttpStatus.OK);
	}
	
	// 삭제할 때 문서일 경우 "d" + documentIdx, 폴더일 경우 "f" + folderIdx 로 넘겨주세요
	@PostMapping("/document/list/trash")
	public ResponseEntity<String> del(@RequestBody final List<String> objectIdxList){//, final int empIdx) {
		System.out.println("Controller의 del 호출 성공");
		int countAll = objectIdxList.size();
		int countFail = 0;
		int empIdx = 1; //테스트용
		StringBuilder sb = new StringBuilder();
		List<Integer> documentIdxList = trashService.seperateObjectList(objectIdxList, 'd');
		List<Integer> folderIdxList = trashService.seperateObjectList(objectIdxList, 'f');
		Set<Integer> removeCandidateSet = new HashSet<>(); // 제외시킬 folderIdx를 담을 set
		for(int documentIdx : documentIdxList) { // 문서먼저 삭제, 삭제할수 없는 문서인 경우 상위 폴더도 삭제목록에서 제거.
			System.out.println("반복문 진입 ");
			int statusCode = documentVersionService.selectByDocumentIdx(documentIdx).getMasterCode().getStatusCode();
			if(documentService.isPublished(documentIdx)) { // 배포된 버전이라면
				countFail++;
				System.out.println("배포된 버전, 삭제 실패 ");
				System.out.println("restDoc" + documentService.select(documentIdx) + documentService.select(documentIdx).getFolder());
				int removeCandidate = documentService.select(documentIdx).getFolder().getFolderIdx();
				for(Folder folder : folderService.selectParentList(removeCandidate)) {
					removeCandidateSet.add(folder.getFolderIdx());
				};
				
				continue;
			}
			if(statusCode > 150 && statusCode < 450) {
				System.out.println("초안 상태가 아니라 실패 ");
				countFail++;
				int removeCandidate = documentService.select(documentIdx).getFolder().getFolderIdx();
				for(Folder folder : folderService.selectParentList(removeCandidate)) {
					removeCandidateSet.add(folder.getFolderIdx());
				};
				continue;
			}
			trashService.insert(documentIdx, empIdx);
			
		}
		folderIdxList.removeAll(removeCandidateSet);
		for(int folderIdx : folderIdxList) {
			System.out.println("폴더 삭제 진행중");
			folderService.deleteFolder(folderIdx, empIdx);
		}
		
		ResponseEntity<String> entity = ResponseEntity.ok(sb.toString() + "총 " + countAll + "개 중 " + (countAll - countFail) + "개 삭제 성공");

		return entity;
	}
	
	@PostMapping("/document/folder")
	public ResponseEntity<String> createFolder(@RequestParam("folderName") final String folderName, 
												@RequestParam("parentFolderIdx") final int parentFolderIdx) {
		log.debug("view로부터 받은 폴더는 : " + folderName + " 부모 폴더: " + parentFolderIdx);
		if(folderName == null || folderName.isEmpty()) {
			throw new IllegalArgumentException("폴더 이름이 비어있습니다.");
		}
		Folder folder = new Folder();
		folder.setFolderName(folderName);
		folder.setParentFolder(folderService.select(parentFolderIdx));
		folder.setProject(folder.getParentFolder().getProject());
		
		folderService.createFolder(folder);
		
		ResponseEntity<String> entity = ResponseEntity.ok("폴더 생성 성공");
		return entity;
	}
	
	@PatchMapping("/document/folder")
	public ResponseEntity<String> moveDocuement(@RequestBody final Map<String, Object> request) {
		List<String> objectIdxList = (List<String>)request.get("objectIdxList");
		Integer targetFolderIdx = Integer.parseInt((String)request.get("targetFolderIdx"));
		
		log.warn("컨트롤러 moveDocument 호출, 선택된 idx: " + objectIdxList);
		List<Integer> documentIdxList = trashService.seperateObjectList(objectIdxList, 'd');
		List<Integer> folderIdxList = trashService.seperateObjectList(objectIdxList, 'f');
		
		for(Integer documentIdx : documentIdxList) { // 문서의 부모 폴더idx를 변경
			Document document = documentService.select(documentIdx);
			Folder folder = document.getFolder();
			folder.setFolderIdx(targetFolderIdx);
			document.setFolder(folder);
			documentService.update(document);
		}
		
		for (Integer folderIdx : folderIdxList) { // 폴더의 부모 폴더idx를 변경
			Folder folder = folderService.select(folderIdx);
			Folder ParentFolder = new Folder();
			ParentFolder.setFolderIdx(targetFolderIdx);
			folder.setParentFolder(ParentFolder);
			folderService.updateFolder(folder);
		}
		
		return ResponseEntity.ok("폴더 이동 완료");
	}
	
	@PatchMapping("/document/folder/name")
	public ResponseEntity<String> renameFolder(@RequestParam("folderIdx") final int folderIdx,@RequestParam("folderName") final String folderName) {
		Folder folder = folderService.select(folderIdx);
		folder.setFolderName(folderName);
		folderService.updateFolder(folder);
		return ResponseEntity.ok("폴더 이름 변경 완료");
	}
	
	//리뷰요청
	@PostMapping("/document/review/request")
	public ResponseEntity reviewRequest(@RequestParam("documentIdx") int documentIdx) {
		DocumentVersion documentVersion  = documentService.documentDetailSelect(documentIdx);
		documentVersion.getDocument().setDocumentIdx(documentIdx);
		log.debug("documentVersion = " + documentVersion);
		
		documentService.documentVersionStatusUpdate(documentVersion);
		
		return null;
	}
	
	//버전관리
	@PostMapping("/document/version/update")
	public ResponseEntity getVersion(@RequestParam("versionLogIdx") int versionLogIdx, 
														@RequestParam("documentIdx") int documentIdx) {
		VersionLog versionLog = new VersionLog();
		versionLog.setVersionLogIdx(versionLogIdx);
		
	    // Document 객체 생성 및 초기화
	    Document document = new Document();
	    document.setDocumentIdx(documentIdx);
	    versionLog.setDocument(document);
		documentService.documentVersionUpdate(versionLog);
		
		
		return null;
	}
	
	@ExceptionHandler({DocumentException.class, VersionLogException.class, FolderException.class})
	public ResponseEntity handle(DocumentException e, VersionLogException e2, FolderException e3) {
		e.printStackTrace();
		e2.printStackTrace();
		e3.printStackTrace();
		
		ResponseEntity entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return entity;

	}
	
	
	
}
