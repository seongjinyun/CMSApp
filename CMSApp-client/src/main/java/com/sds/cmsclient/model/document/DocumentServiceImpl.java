package com.sds.cmsclient.model.document;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsclient.domain.Folder;

@Service
public class DocumentServiceImpl implements DocumentService{
	
	@Autowired
	private DocumentDAO documentDAO;
	
	public List<Folder> folderAllSelect(int projectIdx) {
		return documentDAO.folderAllSelect(projectIdx);
	}
	public int subFolderSelect(int folderIdx) {
		return documentDAO.subFolderSelect(folderIdx);
	}
}
