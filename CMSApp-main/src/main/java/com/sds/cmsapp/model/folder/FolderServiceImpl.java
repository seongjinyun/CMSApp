package com.sds.cmsapp.model.folder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.Folder;
import com.sds.cmsapp.domain.Trash;
import com.sds.cmsapp.model.document.DocumentDAO;
import com.sds.cmsapp.model.emp.EmpDAO;
import com.sds.cmsapp.model.trash.TrashDAO;

@Service
public class FolderServiceImpl implements FolderService {
	
	@Autowired
	private FolderDAO folderDAO;
	
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private TrashDAO trashDAO;
	
	@Autowired
	private EmpDAO empDAO;

	@Override
	public int moveDirectory(int document_idx, int targetFolderIdx) {
		Document document = documentDAO.select(document_idx);
		document.setFolder(folderDAO.select(targetFolderIdx));
		return documentDAO.update(document);
		
	}

	@Override
	public int  createFolder(Folder folder) {
		return folderDAO.insert(folder);
	}

	@Override
	@Transactional
	public void deleteFolder(int folder_idx, int emp_idx) {
		List<Folder> childFolderList = folderDAO.selectSub(folder_idx);
		List<Document> documentList = documentDAO.selectByFolderIdx(folder_idx);
		if(documentList != null) {
			for(Document document : documentList) {
				Trash trash = new Trash();
				trash.setDocument(document);
				trash.setEmp(empDAO.selectByEmpIdx(emp_idx));
				trashDAO.insert(trash);
			}
		}
		if(childFolderList != null) {
			for(Folder folder : childFolderList) {
				deleteFolder(folder.getFolder_idx(), emp_idx);
			}
		}
		folderDAO.delete(folder_idx);
	}

	@Override
	public int updateFolder(Folder folder) {
		return folderDAO.update(folder);
	}
	
	@Override
	public Folder select(int folder_idx) {
		return folderDAO.select(folder_idx); 	
	}

	@Override
	public List<Folder> selectSub(int folder_idx) {
		return folderDAO.selectSub(folder_idx);
	}

	@Override
	public List<Folder> selectAll() {
		return completeTopFolder();
	}
	
	@Override
	public int selectDepth(int folder_idx) {
		int depth = 1;
		Integer parentFolder_idx = folder_idx;
		Folder folder = null;
		while(true) {
			folder = folderDAO.select(parentFolder_idx);
			Folder parentFolder = folder.getParent_folder();
			if (parentFolder == null) {
				break;
			}
			parentFolder_idx = parentFolder.getFolder_idx();
			depth++;
			
		} 
		return depth;
	}
	
	// 현재 폴더부터 최상위 폴더까지 리스트에 담은 후 순서 반전
	@Override
	public List<String> selectPath(int folder_idx) {
		Folder folder = null; 
		Integer parentFolder_idx = folder_idx;
		List<String> folderNameList = new ArrayList<String>();
		folderNameList.add(folderDAO.select(folder_idx).getFolder_name());
		while(true) {
			folder = folderDAO.select(parentFolder_idx);
			Folder parentFolder = folder.getParent_folder();
			if (parentFolder == null) {
				break;
			}
			parentFolder_idx = parentFolder.getFolder_idx();
			folderNameList.add(folderDAO.select(parentFolder_idx).getFolder_name());
		}
		Collections.reverse(folderNameList);
		return folderNameList;
	}
	
	public List<Folder> completeTopFolder(){
		int count = 0;
		List<Folder> topFolderList = folderDAO.selectTopFolder();
		List<Folder> folderList = new ArrayList<Folder>();
		folderList.addAll(topFolderList);
		List<Folder> folderList2 = new ArrayList<Folder>();
		while(true) {
			for (Folder folder : folderList) {			
				int folder_idx = folder.getFolder_idx();				
				List<Folder> subList = folderDAO.selectSub(folder_idx);				
				folder.setChildFolderList(subList);
				for (Folder subFolder : subList) {				
					folderList2.add(subFolder);
					count++;
				}
			}
			folderList.clear();
			if (folderList2.size() == 0) {
				break;
			}
			folderList.addAll(folderList2);
			folderList2.clear();
		}
		return topFolderList;
	}
	
}
