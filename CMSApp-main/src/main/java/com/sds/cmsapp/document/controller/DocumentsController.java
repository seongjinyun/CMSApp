package com.sds.cmsapp.document.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.model.document.DocumentService;

@Controller
public class DocumentsController {

	@Autowired
	private DocumentService documentService;
	
	//글 작성 폼
	@GetMapping("/document/writeform")
	public String getDocument() {

		return "documents/writeform";
	} 
	
	//작성한 글 요청
	@PostMapping("/document/save")
	public String edit(VersionLog versionLog) {
		System.out.println(versionLog.getTitle());
		System.out.println(versionLog.getContent());
		
		documentService.insert(versionLog);
		
		return "redirect:/document/writeform";
	}

	//프로젝트 목록
	@GetMapping("/document/folder_list")
	public String getDocumentFolderList() {
		return "documents/folder_list";
	} 
	
	//파일목록
	@GetMapping("/document/list")
	public String getDocumentList(Model model) {
		
		return "documents/list";
	} 
	
	//휴지통
	@GetMapping("/document/trash")
	public String getRecycleBin() {
		return "documents/trash";
	} 
	
	//즐겨찾기
	@GetMapping("/document/bookmark")
	public String getBookmark() {
		return "documents/bookmark";
	} 
	//전체보기 (프로젝트 목록)
	@GetMapping("/document/project_list")
	public String getProjectList() {
		return "documents/document_project_list";
	} 
	// 글 상세보기
	@GetMapping("/document/detail")
	public String getDetail() {
		return "documents/detail";
	}
}
