package com.sds.cmsapp.model.document;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.VersionLog;

@Mapper
public interface DocumentDetailDAO {
	public DocumentVersion documentDetailSelect(int documentIdx);
	public int versionLogInsert(VersionLog versionLog);
	public int documentVersionUpdate(VersionLog versionLog);
	public void documentVersionStatusUpdate(DocumentVersion documentVersion);
	
    public int findMaxVersionByDocumentIdx(int documentIdx);

}