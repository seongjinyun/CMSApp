package com.sds.cmsdocument.model.folder;

import java.util.List;

import com.sds.cmsdocument.domain.Folder;
import com.sds.cmsdocument.exception.FolderException;

public interface FolderService {
	
	public int moveDirectory(final int documentIdx, final int targetFolderIdx);
	
	public int createFolder(final Folder folder);
	
	public void deleteFolder(final int folderIdx, final int empIdx);
	
	public int updateFolder(final Folder folder);
	
	public Folder select(final int folderIdx);
	
	public List<Folder> selectSub(final int folderIdx);
	
	public List<Folder> selectAll(); // 하위 폴더가 채워지지 않은 전체 폴더만 반환합니다(DB에서 가공없이 가져옴)
	
	public int selectDepth(final int folderIdx); // 최상위가 1
	
	public List<String> selectPath(final int folderIdx); // 최상위 폴더명부터 현재 폴더명까지 차례로 리스트로 반환
	
	public List<Folder> completeFolderList(final List<Folder> folderList); // 폴더 리스트의 DTO에 하위 폴더 끝까지 채우기
	
	public List<Folder> selectTopFolder(); // 최상위폴더 반환
	
	public Folder completeFolder(final int folderIdx) throws FolderException; // 폴더 하나의 하위폴더를 끝까지 채우기
	
	public Folder fillDocument(final int folderIdx); // 해당 폴더 DTO에 존재하는 문서들을 List로 추가합니다.
	
	public Folder completeFolderWithDocument(final int folderIdx) throws FolderException; // 폴더 하나를 문서를 포함하여 끝까지 채우기
	
	public List<Folder> selectByProjectIdx(final int projectIdx); // 프로젝트 ID로 폴더 리스트 조회
	
	public Folder selectProjectRootFolder(final int projectIdx); // 프로젝트와 동일시되는 최상위폴더 조회
	
	public List<Folder> selectParentList(final int folderIdx); // 부모가 1번, 인덱스를 입력한 자식이 마지막인 리스트 반환
		
	public Folder selectRestoreFolder(); // 상위 폴더가 삭제됐을 때 임시로 가는 폴더

}
