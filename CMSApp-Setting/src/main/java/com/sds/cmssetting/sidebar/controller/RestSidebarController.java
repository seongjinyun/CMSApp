package com.sds.cmssetting.sidebar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmssetting.domain.Folder;
import com.sds.cmssetting.exception.FolderException;
import com.sds.cmssetting.model.folder.FolderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestSidebarController {
	
	@Autowired
	private FolderService folderService;
	
	@GetMapping("/sidebar/folder")
	public ResponseEntity<List<Folder>> getFolderList() throws FolderException {
		List<Folder> folderList = folderService.selectTopFolder();
		for(int i = 0; i < folderList.size(); i++) {
			Folder folder = folderList.get(i);
			folder = folderService.completeFolderWithDocument(folder.getFolderIdx());
			folderList.set(i, folder);
		}
		//log.debug("폴더 리스트 받아갔습니다 sidebarContr" + folderList);
		return ResponseEntity.ok(folderList);
	}
	
	@GetMapping("/sidebar/folder/sub")
	public ResponseEntity<List<Folder>> getFolder(@RequestParam("folderIdx") final int folderIdx) throws FolderException {
		Folder folder = folderService.completeFolderWithDocument(folderIdx);
		log.debug("폴더 받아갔습니다 sidebarContr" + folderIdx);
		List<Folder> folderList = new ArrayList<>();
		folderList.add(folder);
		return ResponseEntity.ok(folderList);
	}
	
	@ExceptionHandler(FolderException.class)
	public ResponseEntity handle(FolderException e) {
		e.printStackTrace();
		
		ResponseEntity entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return entity;
		
	}
	
	
	
}
