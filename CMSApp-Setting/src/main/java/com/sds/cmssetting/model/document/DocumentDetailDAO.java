package com.sds.cmssetting.model.document;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmssetting.domain.DocumentVersion;
import com.sds.cmssetting.domain.VersionLog;

@Mapper
public interface DocumentDetailDAO {
	public DocumentVersion documentDetailSelect(int documentIdx);
	public int versionLogInsert(VersionLog versionLog);
	public int documentVersionUpdate(VersionLog versionLog);
	public void documentVersionStatusUpdate(DocumentVersion documentVersion);
	
    public int findMaxVersionByDocumentIdx(int documentIdx);
    public List<VersionLog> getVersionLogSelect(int documentIdx);

    public void versionLogDelete(int versionLog);
}