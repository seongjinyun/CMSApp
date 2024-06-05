package com.sds.cmsapp.model.folder;

import java.util.List;

import com.sds.cmsapp.domain.Folder;

public interface FolderService {
	
	public int moveDirectory(int document_idx, int targetFolderIdx);
	
	public int createFolder(Folder folder);
	
	public void deleteFolder(int folder_idx, int emp_idx);
	
	public int updateFolder(Folder folder);
	
	public Folder select(int folder_idx);
	
	public List<Folder> selectSub(int folder_idx);
	
	public List<Folder> selectAll(); // has a 관계로 끝까지 꽉 채워서 반환 (최상위 폴더만 반환합니다)
	
	public int selectDepth(int folder_idx); // 최상위가 1
	
	public List<String> selectPath(int folder_idx); // 최상위 폴더명부터 현재 폴더명까지 차례로 리스트로 반환

}
