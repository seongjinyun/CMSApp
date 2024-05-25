package com.sds.cmsapp.document.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentsController {

	//글 작성 폼
	@GetMapping("/document")
	public String getDocument() {
		return "documents/document";
	} 

	//프로젝트 목록
	@GetMapping("/document_folder_list")
	public String getDocumentFolderList() {
		return "documents/document_folder_list";
	} 
	
	//파일목록
	@GetMapping("/document_list")
	public String getDocumentList() {
		return "documents/document_list";
	} 
	
	//휴지통
	@GetMapping("/document_trash")
	public String getRecycleBin() {
		return "documents/document_trash";
	} 
	
	//즐겨찾기
	@GetMapping("/document_bookmark")
	public String getBookmark() {
		return "documents/document_bookmark";
	} 
}
