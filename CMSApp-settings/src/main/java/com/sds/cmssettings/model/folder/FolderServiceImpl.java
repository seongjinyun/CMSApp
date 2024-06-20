package com.sds.cmssettings.model.folder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmssettings.domain.Document;
import com.sds.cmssettings.domain.Folder;
import com.sds.cmssettings.domain.Trash;
import com.sds.cmssettings.exception.FolderException;
import com.sds.cmssettings.model.document.DocumentDAO;
import com.sds.cmssettings.model.document.DocumentService;
import com.sds.cmssettings.model.emp.EmpDAO;
import com.sds.cmssettings.model.trash.TrashDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public int  createFolder(final Folder folder) {
		return folderDAO.insert(folder);
	}

	@Override
	@Transactional
	public void deleteFolder(final int folderIdx, final int empIdx) throws FolderException {
		deleteFolder(folderIdx, empIdx, 0);
	}
	
	private void deleteFolder(final int folderIdx, final  int empIdx, final int depth) throws FolderException {
	
		if (depth > 1000) { // 재귀 깊이 제한
			throw new FolderException("재귀 깊이 초과: 폴더 삭제 과정에서 문제가 발생했습니다. 하위 폴더부터 삭제해주세요.");
		}
		List<Folder> childFolderList = folderDAO.selectSub(folderIdx);
		List<Document> documentList = documentDAO.selectByFolderIdx(folderIdx);
		if(documentList != null) {
			for(Document document : documentList) {
				Folder folder = document.getFolder();
				folder.setFolderIdx(null);
				System.out.println(folder);
				document.setFolder(folder); // document의 folder를 restored로
				documentDAO.update(document);
				Trash trash = new Trash();
				trash.setDocument(document);
				trash.setEmp(empDAO.selectByEmpIdx(empIdx));
				trashDAO.insert(trash);
			}
		}
		if(childFolderList != null) {
			for(Folder folder : childFolderList) {
				deleteFolder(folder.getFolderIdx(), empIdx, depth + 1);

			}
		}
		folderDAO.delete(folderIdx);
	}
	
	@Override
	public Folder selectProjectRootFolder(final int projectIdx) {
		Folder result = new Folder();
		List<Folder> folderList = folderDAO.selectByProjectIdx(projectIdx);
		for(Folder folder : folderList) {
			if(folder.getParentFolder() == null) {
				result = folder;
			}
		}
		return result;
	}
}
