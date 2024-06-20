package com.sds.cmsdocument.model.publishing;

import java.util.List;

import com.sds.cmsdocument.domain.PublishedVersion;
import com.sds.cmsdocument.domain.PublishedVersionName;

public interface PublishedVersionService {
	
	public List<PublishedVersion> selectWaitingQueue();
	
	public List<PublishedVersion> selectWaitingQueue(PublishedVersionName publishedVersionName);
	
	public PublishedVersionName registPublishedVersionName(PublishedVersionName publishedVersionName);

	public void registPublishedVersion(List<PublishedVersion> publishedVerList);
	
}
