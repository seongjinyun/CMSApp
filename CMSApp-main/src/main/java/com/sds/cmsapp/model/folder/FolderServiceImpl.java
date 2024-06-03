package com.sds.cmsapp.model.folder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void moveDirectory(int document_idx, int targetFolderIdx) {
		Document document = documentDAO.select(document_idx);
		document.setFolder(folderDAO.select(targetFolderIdx));
		documentDAO.update(document);
	}

	@Override
	public void createFolder(Folder folder) {
		folderDAO.insert(folder);
	}

	@Override
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
	public void updateFolder(Folder folder) {
		folderDAO.update(folder);
	}

	@Override
	public List<Folder> selectSub(int folder_idx) {
		return folderDAO.selectSub(folder_idx);
	}

	@Override
	public List<Folder> selectAll() {
		return folderDAO.selectAll();
	}

}
