package com.sds.cmsapp.document.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.cmsapp.common.Pager;
import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.Trash;
import com.sds.cmsapp.model.document.DocumentService;
import com.sds.cmsapp.model.trash.TrashService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DocumentsController {
	
	@Autowired
	private Pager pager;

	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private TrashService trashService;
	

	
	//글 작성 폼
	@GetMapping("/document/writeform")
	public String getDocument(Model model,@RequestParam(value="folderIdx") int folderIdx) {
		
		model.addAttribute("folderIdx", folderIdx);
		return "documents/writeform";
	} 

	//프로젝트 목록
	@GetMapping("/document/folder_list")
	public String getDocumentFolderList() {
		
		return "documents/folder_list";
	} 
	
	//파일목록
	@GetMapping("/document/list")

	public String getDocumentList(Model model, DocumentVersion documentVersion, @RequestParam(value="folderIdx", defaultValue = "0") int folderIdx) {
		if (folderIdx == 0) {
			HashMap<String, Integer> map=new HashMap<String, Integer>();
			map.put("startIndex", pager.getStartIndex());
			map.put("rowCount", pager.getPageSize());
			List<Document> documentList = documentService.selectAll(map);
			model.addAttribute("documentListSelect", documentList);
			model.addAttribute("folderIdx", folderIdx);
			return "documents/list";
		}else {
			HashMap map = new HashMap();
			map.put("folderIdx", folderIdx);	
			//폴더 -> 파일 리스트
			List documentListSelect = documentService.documentListSelect(map);
			
			model.addAttribute("documentListSelect", documentListSelect);
			log.debug("model= " + model);
			
			model.addAttribute("folderIdx", folderIdx);
			
			return "documents/list";
		}
	} 
	//휴지통
	@GetMapping("/document/trash")
	public String getTrash(Model model, @RequestParam(value="currentPage", defaultValue="1") int currentPage) {
		pager.init(trashService.selectCount(), currentPage);
		HashMap<String, Integer> map=new HashMap();
		map.put("startIndex", pager.getStartIndex());
		map.put("rowCount", pager.getPageSize());
		List<Trash> trashList = trashService.selectAllWithRange(map);
		model.addAttribute(trashList);
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
		return "documents/project_list";
	} 
	// 글 상세보기
	@GetMapping("/document/detail")
	public String getDetail(@RequestParam("documentIdx") int documentIdx,
							            @RequestParam("folderIdx") int folderIdx,
							            Model model) {
		DocumentVersion documentVersion  = documentService.documentDetailSelect(documentIdx);
        model.addAttribute("documentVersion", documentVersion);
        model.addAttribute("folderIdx", folderIdx);
        model.addAttribute("documentIdx", documentIdx);
		return "documents/detail";
	}
	
	// 글 수정하기
	@GetMapping("/document/editform")
	public String getEdit(@RequestParam("documentIdx") int documentIdx,
            						@RequestParam("folderIdx") int folderIdx,
            						Model model) {
		DocumentVersion documentVersion  = documentService.documentDetailSelect(documentIdx);
		model.addAttribute("documentVersion", documentVersion);
        model.addAttribute("folderIdx", folderIdx);
        model.addAttribute("documentIdx", documentIdx);
        model.addAttribute("versionLogIdx", documentVersion.getVersionLog().getVersionLogIdx());
        return "documents/editform";
	}
}
