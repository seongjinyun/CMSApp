package com.sds.cmsapp.document.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentRequest;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.model.document.DocumentService;

@RestController
public class RestDocumentController {
	
	@Autowired
	private DocumentService documentService;
	
	@PostMapping("/document/save")
	public ResponseEntity createDocument(@ModelAttribute DocumentRequest documentRequest) {
		try {
            Document document = documentRequest.getDocument();
            VersionLog versionLog = documentRequest.getVersionLog();
            
            System.out.println(document);
            System.out.println(versionLog);
            versionLog.setDocument(document);

            documentService.documentInsert(document, versionLog);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/document/list"); // 리다이렉트할 URL 설정
            return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Found 상태 반환
            
        } catch (Exception e) {
        	HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "http://example.com/error?message=" + e.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Found 상태 반환
        }
    }
}
