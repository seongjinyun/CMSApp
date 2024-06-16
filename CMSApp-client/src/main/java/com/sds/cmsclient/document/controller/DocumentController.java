package com.sds.cmsclient.document.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentController {
	
	@GetMapping("/document")
	public String getDocument() {
		return "document/document";
	}
}
