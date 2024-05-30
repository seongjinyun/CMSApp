package com.sds.cmsapp.model.folder;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Folder;

@Mapper
public interface FolderDAO {
	
	// 폴더 추가
	public int insert(Folder folder);
	
	// 폴더 제거
	public int delete(int folder_idx);
	
	// 폴더 수정
	public int update(Folder folder);
	
	// 폴더 한건 조회
	public Folder select(int folder_idx);
	
	// 모든폴더 조회
	public List<Folder> selectAll();
	
	// 하위폴더 조회
	public List<Folder> selectSub(int folder_idx);
		
	// 상위폴더 조회
	public Folder selectParent(Folder folder);
	
	
}
