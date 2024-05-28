package com.sds.cmsapp.document.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentsController {

	//글 작성 폼
	@GetMapping("/document")
	public String getDocument() {
		return "documents/writeform";
	} 

	//프로젝트 목록
	@GetMapping("/document/folder_list")
	public String getDocumentFolderList() {
		return "documents/folder_list";
	} 
	
	//파일목록
	@GetMapping("/document/list")
	public String getDocumentList() {
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
