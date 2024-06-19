package com.sds.cmsclient.model.document;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsclient.domain.Folder;
import com.sds.cmsclient.domain.PublishedVersion;

@Mapper
public interface DocumentDAO {
	public List<Folder> getFolderListByProjectIdx(int projectIdx);
	public List<PublishedVersion> getPublishedVersionByDocumentIdx(int documentIdx);
	public String projectNameSelect(int projectIdx);
}
