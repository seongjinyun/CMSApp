package com.sds.cmsapp.document.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.cmsapp.common.Pager;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.Trash;
import com.sds.cmsapp.model.document.DocumentService;
import com.sds.cmsapp.model.folder.FolderService;
import com.sds.cmsapp.model.trash.TrashService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DocumentsController {
	
	@Autowired
	Pager pager;

	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private TrashService trashService;
	

	
	//글 작성 폼
	@GetMapping("/document/writeform")
	public String getDocument(Model model,@RequestParam(value="folder_idx") int folder_idx) {
		
		model.addAttribute("folder_idx", folder_idx);
		return "documents/writeform";
	} 

	//프로젝트 목록
	@GetMapping("/document/folder_list")
	public String getDocumentFolderList() {
		
		return "documents/folder_list";
	} 
	
	//파일목록
	@GetMapping("/document/list")
	public String getDocumentList(Model model, DocumentVersion documentVersion, @RequestParam(value="folder_idx") int folder_idx) {
		HashMap map = new HashMap();
		map.put("folder_idx", folder_idx);	
		//폴더 -> 파일 리스트
		List documentListSelect = documentService.documentListSelect(map);
		
		model.addAttribute("documentListSelect", documentListSelect);
		log.debug("model= " + model);
		
		model.addAttribute("folder_idx", folder_idx);
		
		return "documents/list";
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
	public String getDetail(@RequestParam("document_idx") int documentIdx,
							            @RequestParam("folder_idx") int folderIdx,
							            Model model, DocumentVersion documentVersion) {
		DocumentVersion documentDetail = documentService.documentDetailSelect(documentVersion);
		model.addAttribute("documentDetail", documentDetail);
		return "documents/detail";
	}
	

}
