package com.sds.cmsclient.document.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.cmsclient.domain.Folder;
import com.sds.cmsclient.domain.Project;
import com.sds.cmsclient.domain.PublishedVersion;
import com.sds.cmsclient.model.document.DocumentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DocumentController {
	
	@Autowired
	private DocumentService documentService;
	
    @GetMapping("/document/project")
    public String getFolderList(@RequestParam("projectIdx") int projectIdx,Model model) {
        List<Folder> folderList = documentService.getFolderListByProjectIdx(projectIdx);
        String projectName = documentService.projectNameSelect(projectIdx);
        List<Integer> folderIdx = folderList.stream()
                .map(Folder::getFolderIdx)
                .collect(Collectors.toList());
        
        model.addAttribute("folderList", folderList);
        model.addAttribute("folderIdx", folderIdx);
        model.addAttribute("projectIdx", projectIdx);
        model.addAttribute("projectName", projectName);
        return "document/document";
    }
    

}
