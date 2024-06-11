package com.sds.cmsapp.sidebar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.domain.Folder;
import com.sds.cmsapp.exception.FolderException;
import com.sds.cmsapp.model.folder.FolderService;

@RestController
public class RestSidebarController {
	
	@Autowired
	private FolderService folderService;
	
	@GetMapping("/sidebar/folder")
	public List<Folder> getFolderList() throws FolderException {
		List<Folder> folderList = folderService.selectTopFolder();
		folderList.forEach(folder -> {
			try {
				folderService.completeFolderWithDocument(folder.getFolderIdx());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		});
		return folderList;
	}
	
	@ExceptionHandler(FolderException.class)
	public ResponseEntity handle(FolderException e) {
		e.printStackTrace();
		
		ResponseEntity entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return entity;
		
	}
	
	
	
}
