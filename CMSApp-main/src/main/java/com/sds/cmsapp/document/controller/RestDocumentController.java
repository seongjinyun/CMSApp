package com.sds.cmsapp.document.controller;

import java.util.List;

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
	
	@GetMapping("document/folder/list")
	public ResponseEntity getFolderList() throws FolderException {
		List<Folder> folderList = folderService.selectTopFolder();
		for(int i = 0; i < folderList.size(); i++) {
			Folder folder = folderList.get(i);
			folder = folderService.completeFolder(folder.getFolderIdx());
			folderList.set(i, folder);
		}
		return new ResponseEntity<>(folderList, HttpStatus.OK);
	}
	
	@PostMapping("document/list/trash")
	public ResponseEntity goToTrash(List<Integer> documentIdxList, int empIdx) {
		for(int documentIdx : documentIdxList) {
			trashService.insert(documentIdx, empIdx);
		}
		ResponseEntity entity = ResponseEntity.ok("삭제 성공");

		return entity;
	}
	
	@PostMapping
	public ResponseEntity createFolder(int folderIdx, String folderName, int empIdx) {
		Folder folder = new Folder();
		
		folderService.createFolder(null);
		
		ResponseEntity entity = ResponseEntity.ok("폴더 생성 성공");
		return entity;
	}
	
	@PatchMapping("document/folder")
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


