package com.sds.cmsapp.model.folder;

import java.util.List;

import com.sds.cmsapp.domain.Folder;
import com.sds.cmsapp.exception.FolderException;

public interface FolderService {
	
	public int moveDirectory(int document_idx, int targetFolderIdx);
	
	public int createFolder(Folder folder);
	
	public void deleteFolder(int folder_idx, int emp_idx);
	
	public int updateFolder(Folder folder);
	
	public Folder select(int folder_idx);
	
	public List<Folder> selectSub(int folder_idx);
	
	public List<Folder> selectAll(); // 하위 폴더가 채워지지 않은 전체 폴더만 반환합니다(DB에서 가공없이 가져옴)
	
	public int selectDepth(int folder_idx); // 최상위가 1
	
	public List<String> selectPath(int folder_idx); // 최상위 폴더명부터 현재 폴더명까지 차례로 리스트로 반환
	
	public List<Folder> completeFolderList(List<Folder> folderList); // 폴더 리스트의 DTO에 하위 폴더 끝까지 채우기
	
	public List<Folder> selectTopFolder(); // 최상위폴더 반환
	
	public Folder completeFolder(int folder_idx) throws FolderException; // 폴더 하나를 끝까지 채우기
	
	public Folder fillDocument(int folder_idx); // 해당 폴더 DTO에 존재하는 문서들을 List로 추가합니다.
	
	public Folder completeFolderWithDocument(int folder_idx) throws Throwable; // 폴더 하나를 문서를 포함하여 끝까지 채우기
	
}
