package com.sds.cmsapp.document.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentRequest;
import com.sds.cmsapp.domain.Folder;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.exception.DocumentException;
import com.sds.cmsapp.exception.VersionLogException;
import com.sds.cmsapp.model.document.DocumentService;
import com.sds.cmsapp.model.folder.FolderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestDocumentController {

	@Autowired
	private DocumentService documentService;

	@Autowired
	private FolderService folderService;

	@PostMapping("/document/save")
	public ResponseEntity createDocument(@ModelAttribute DocumentRequest documentRequest) {
		Document document = documentRequest.getDocument();
		VersionLog versionLog = documentRequest.getVersionLog();

		log.debug("document 안의 folder_idx " + document.getFolder().getFolderIdx());
		log.debug("document 안의 emp_idx is " + document.getEmp().getEmpIdx());
		log.debug("version log title is " + versionLog.getTitle());
		log.debug("version log content is " + versionLog.getContent());

		versionLog.setDocument(document);

		documentService.documentInsert(versionLog);

		ResponseEntity entity = ResponseEntity.ok("DB 입력 성공");

		return entity;
	}

	@ExceptionHandler({ DocumentException.class, VersionLogException.class })
	public ResponseEntity handle(DocumentException e, VersionLogException e2) {
		e.printStackTrace();
		e2.printStackTrace();

		ResponseEntity entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return entity;

	}

	@GetMapping("document/folder/list")
	private ResponseEntity getFolderList() {
		List<Folder> folderList = folderService.selectAll();
		System.out.println("FolderList 는 " + folderList);
		return new ResponseEntity<>(folderList, HttpStatus.OK);
	}

}
