package com.sds.cmsapp.document.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentRequest;
import com.sds.cmsapp.domain.Folder;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.exception.DocumentException;
import com.sds.cmsapp.exception.FolderException;
import com.sds.cmsapp.exception.VersionLogException;
import com.sds.cmsapp.model.document.DocumentService;
import com.sds.cmsapp.model.document.DocumentVersionService;
import com.sds.cmsapp.model.folder.FolderService;
import com.sds.cmsapp.model.project.ProjectService;
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
	
	@Autowired
	private ProjectService projectService;
	

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
	public ResponseEntity<String> goToTrash(final List<String> objectIdxList, final int empIdx) {
		int countAll = objectIdxList.size();
		int countFail = 0;
		List<Integer> documentIdxList = trashService.seperateObjectList(objectIdxList, 'd');
		List<Integer> folderIdxList = trashService.seperateObjectList(objectIdxList, 'f');
		Set<Integer> removeCandidateSet = new HashSet<>(); // 제외시킬 folderIdx를 담을 set
		for(int documentIdx : documentIdxList) { // 문서먼저 삭제, 삭제할수 없는 문서인 경우 상위 폴더도 삭제목록에서 제거.
			int statusCode = documentVersionService.selectByDocumentIdx(documentIdx).getStatusCode().getStatusCode();
			if(documentService.isPublished(documentIdx)) { // 배포된 버전이라면
				countFail++;
				int removeCandidate = documentService.select(documentIdx).getFolder().getFolderIdx();
				for(Folder folder : folderService.selectParentList(removeCandidate)) {
					removeCandidateSet.add(folder.getFolderIdx());
				};
				
				continue;
			}
			if(statusCode > 150 && statusCode < 450) {
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
			folderService.deleteFolder(folderIdx, empIdx);
		}
		ResponseEntity<String> entity = ResponseEntity.ok("총 " + countAll + "개 중 " + (countAll - countFail) + "개 삭제 성공");

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
	public ResponseEntity moveDocuement(@RequestParam("documentIdxList")List<Integer> documentIdxList) {
		log.debug("컨트롤러 moveDocument 호출, 선택된 idx: " + documentIdxList);
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


