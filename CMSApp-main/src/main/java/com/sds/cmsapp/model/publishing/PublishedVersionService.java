package com.sds.cmsapp.model.publishing;

import java.util.List;

import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.PublishedVersion;
import com.sds.cmsapp.domain.PublishedVersionName;

public interface PublishedVersionService {
	
	public List<PublishedVersion> selectWaitingQueue();
	
	public List<PublishedVersion> selectWaitingQueue(PublishedVersionName publishedVersionName);
	
	public PublishedVersionName registPublishedVersionName(PublishedVersionName publishedVersionName);

	public void registPublishedVersion(List<PublishedVersion> publishedVerList);
	
}
