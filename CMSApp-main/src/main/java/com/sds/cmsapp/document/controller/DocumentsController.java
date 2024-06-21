package com.sds.cmsapp.document.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.cmsapp.common.Pager;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.Folder;
import com.sds.cmsapp.domain.Trash;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.model.document.DocumentEditingService;
import com.sds.cmsapp.model.document.DocumentService;
import com.sds.cmsapp.model.folder.FolderService;
import com.sds.cmsapp.model.trash.TrashService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DocumentsController {
	
    @Autowired
    private DocumentEditingService editingService;
    
	@Autowired
	private Pager pager;

	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private TrashService trashService;
	
	@Autowired
	private FolderService folderService;
	
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

	public String getDocumentList(Model model, DocumentVersion documentVersion, @RequestParam(value="folderIdx", defaultValue = "0") final int folderIdx) {
		if (folderIdx == 0) {
			HashMap<String, Integer> map=new HashMap<String, Integer>();
			map.put("startIndex", pager.getStartIndex());
			map.put("rowCount", pager.getPageSize());
			Folder folder = new Folder();
			folder.setFolderName("전체보기");
			folder.setFolderIdx(0);
			List<Folder> subFolderList = folderService.selectTopFolder();
			List<Folder> parentFolderList = new ArrayList<>();
			parentFolderList.add(folder);
			List<DocumentVersion> documentList = documentService.selectAllOrigin();
			model.addAttribute("documentListSelect", documentList);
			model.addAttribute("folderIdx", folderIdx);
			model.addAttribute("folder", folder);
			model.addAttribute("parentFolderList", parentFolderList);
			model.addAttribute("subFolderList", subFolderList);
			return "documents/list";
		}else {
			HashMap<String, Integer> map = new HashMap<>();
			map.put("folderIdx", folderIdx);
			Folder folder = folderService.completeFolderWithDocument(folderIdx);
			//폴더 -> 파일 리스트
			List<DocumentVersion> documentListSelect = documentService.documentListSelect(map);
			List<Folder> parentFolderList = folderService.selectParentList(folderIdx);
			List<Folder> subFolderList = folderService.selectSub(folderIdx);
			
			model.addAttribute("documentListSelect", documentListSelect);
			log.debug("model= " + model);
			
			model.addAttribute("folderIdx", folderIdx);
			model.addAttribute("folder", folder);
			Collections.reverse(parentFolderList);
			model.addAttribute("parentFolderList", parentFolderList);
			model.addAttribute("subFolderList", subFolderList);
			model.addAttribute("restoredFolderIdx", folderService.selectRestoreFolder().getFolderIdx());
			return "documents/list";
		}
	} 

	//휴지통
	@GetMapping("/document/trash")
	public String getTrash(Model model, @RequestParam(value="currentPage", defaultValue="1") int currentPage) {
		pager.init(trashService.selectCount(), currentPage);
		Map<String, Integer> map = new HashMap<>();
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
		documentVersion.getVersionLog().setEmp(new Emp());
		documentVersion.getVersionLog().getEmp().setEmpIdx(3); //empIdx 넘어올 자리
		System.out.println(documentVersion.getVersionLog().getEmp().getEmpIdx());

		
        List<VersionLog> versionLogs = documentService.getVersionLogSelect(documentIdx);
        List<Folder> folderList = folderService.selectParentList(folderIdx);
        model.addAttribute("folderList", folderList);
        model.addAttribute("versionLogs", versionLogs);
        model.addAttribute("documentVersion", documentVersion);
        model.addAttribute("folderIdx", folderIdx);
        model.addAttribute("documentIdx", documentIdx);
		return "documents/detail";
	}
	
	// 글 수정폼
	@GetMapping("/document/editform")
	public String getEdit(@RequestParam("documentIdx") int documentIdx,
            						@RequestParam("folderIdx") int folderIdx,
            						Model model) {
        // 이미 다른 사용자가 수정 중이면 접근을 막음
        if (editingService.isDocumentBeingEdited(documentIdx)) {
            model.addAttribute("serverMessage", "다른 사용자가 수정 중인 문서입니다.");
            return "redirect:/document/list"; // 예시로 리스트 페이지로 리다이렉트
        }
        
        // 해당 문서를 수정 중으로 표시
        editingService.addEditingDocument(documentIdx);
        
		DocumentVersion documentVersion  = documentService.documentDetailSelect(documentIdx);
		model.addAttribute("documentVersion", documentVersion);
        model.addAttribute("folderIdx", folderIdx);
        model.addAttribute("documentIdx", documentIdx);
        model.addAttribute("versionLogIdx", documentVersion.getVersionLog().getVersionLogIdx());
        return "documents/editform";
	}
	

}
