package com.sds.cmsclient.document.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.cmsclient.domain.Folder;
import com.sds.cmsclient.domain.Project;
import com.sds.cmsclient.model.document.DocumentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DocumentController {
	
	@Autowired
	private DocumentService documentService;
	
	@GetMapping("/document/project")
	public String getFolderList(@RequestParam("projectIdx") int projectIdx, Model model) {
		
	     // 현재 프로젝트의 최상위폴더
        List<Folder> subFolders = documentService.topFolderSelect(projectIdx);
        
        log.debug("subFolders = " + subFolders);
        
        
        //현재 프로젝트 하위 폴더
       // int parentFolderIdx = documentService.subFolderSelect(projectIdx);

        model.addAttribute("subFolders", subFolders);
        //model.addAttribute("parentFolderIdx", parentFolderIdx);
        
        return "document/document";
	}
}
