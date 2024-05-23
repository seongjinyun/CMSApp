package com.sds.cmsapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentsController {

	@GetMapping("/document")
	public String getDocument() {
		return "documents/document";
	} 

	@GetMapping("/document_list")
	public String getDocumentsList() {
		return "documents/document_list";
	} 
}
