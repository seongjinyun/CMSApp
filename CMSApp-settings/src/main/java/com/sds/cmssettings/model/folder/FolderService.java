package com.sds.cmssettings.model.folder;

import java.util.List;

import com.sds.cmssettings.domain.Folder;
import com.sds.cmssettings.exception.FolderException;

public interface FolderService {
	
	public int createFolder(final Folder folder);
	
	public void deleteFolder(final int folderIdx, final int empIdx);
	
	public Folder selectProjectRootFolder(final int projectIdx); // 프로젝트와 동일시되는 최상위폴더 조회
	
}
