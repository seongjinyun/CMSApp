package com.sds.cmsapp.model.folder;

import java.util.List;

import com.sds.cmsapp.domain.Folder;

public interface FolderService {
	
	public void moveDirectory(int document_idx, int targetFolderIdx);
	
	public void createFolder(Folder folder);
	
	public void deleteFolder(int folder_idx, int emp_idx);
	
	public void updateFolder(Folder folder);
	
	public List<Folder> selectSub(int folder_idx);
	
	public List<Folder> selectAll();
	

}
