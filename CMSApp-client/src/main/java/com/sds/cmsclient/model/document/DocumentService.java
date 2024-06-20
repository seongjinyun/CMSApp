package com.sds.cmsclient.model.document;

import java.util.List;

import com.sds.cmsclient.domain.Folder;
import com.sds.cmsclient.domain.PublishedVersion;

public interface DocumentService {
	 public List<Folder> getFolderListByProjectIdx(int projectIdx);
	 public List<PublishedVersion> getPublishedVersionByDocumentIdx(int documentIdx);
	 public String projectNameSelect(int projectIdx);

}
