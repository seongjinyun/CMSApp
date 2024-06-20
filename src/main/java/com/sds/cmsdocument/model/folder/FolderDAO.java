package com.sds.cmsdocument.model.folder;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sds.cmsdocument.domain.Folder;
import com.sds.cmsdocument.domain.RequestDocFilterDTO;

@Mapper
public interface FolderDAO {
	
	// 폴더 추가
	public int insert(final Folder folder);
	
	// 폴더 제거
	public int delete(final int folderIdx);
	
	// 폴더 수정
	public int update(final Folder folder);
	
	// 폴더 한건 조회
	public Folder select(final int folderIdx);
	
	// 모든폴더 조회
	public List<Folder> selectAll();
	
	// 하위폴더 조회
	public List<Folder> selectSub(final int folderIdx);
		
	// 상위폴더 조회
	public Integer selectParentIdx(final int folderIdx);
	
	// 최상위 폴더 조회
	public List<Folder> selectTopFolder();
	
	// 폴더 목록 조회
	public List<Integer> selectFolderIdxListInProject(RequestDocFilterDTO requestDocumentDTO);
	
	// 프로젝트 idx로 폴더 조회
	public List<Folder> selectByProjectIdx(int projectIdx);
	
	/**
	 * 복원된 문서들이 갈 폴더 조회 메서드
	 * @return: Folder
	 * @Method info: 최초의 restored 라는 이름을 가진 폴더가 insert 되어야 합니다
	 * test
	 */
	public Folder selectRestoreFolder();
	
	
}
