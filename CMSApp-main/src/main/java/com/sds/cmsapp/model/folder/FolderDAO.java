package com.sds.cmsapp.model.folder;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Folder;

@Mapper
public interface FolderDAO {
	
	// 폴더 추가
	public int insert(Folder folder);
	
	// 폴더 제거
	public int delete();
	
	// 부모가 누구야 (없으면 null)
	public Folder selectParent(int folder_idx);
	
	// 자식이 누구야 
	public List selectChildren(int folder_idx);
	
	
	
}
