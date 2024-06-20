package com.sds.cmsclient.document.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsclient.domain.PublishedVersion;
import com.sds.cmsclient.domain.VersionLog;
import com.sds.cmsclient.model.document.DocumentService;

@RestController
public class RestDocumentController {

	@Autowired
	private DocumentService documentService;
	
    @GetMapping("/document/detail")
    public ResponseEntity<PublishedVersion> getDocument(@RequestParam("projectIdx") int projectIdx, @RequestParam("documentIdx") int documentIdx) {
        List<PublishedVersion> publishedVersions = documentService.getPublishedVersionByDocumentIdx(documentIdx);
        
        if (!publishedVersions.isEmpty()) {
            PublishedVersion publishedVersion = publishedVersions.get(0);
            
            // 이후에 필요한 처리를 하거나 ResponseEntity로 반환합니다.
            return ResponseEntity.ok(publishedVersion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
