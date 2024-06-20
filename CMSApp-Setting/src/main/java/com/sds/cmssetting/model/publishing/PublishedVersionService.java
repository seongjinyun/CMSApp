package com.sds.cmssetting.model.publishing;

import java.util.List;

import com.sds.cmssetting.domain.PublishedVersion;
import com.sds.cmssetting.domain.PublishedVersionName;

public interface PublishedVersionService {
	
	public List<PublishedVersion> selectWaitingQueue();
	
	public List<PublishedVersion> selectWaitingQueue(PublishedVersionName publishedVersionName);
	
	public PublishedVersionName registPublishedVersionName(PublishedVersionName publishedVersionName);

	public void registPublishedVersion(List<PublishedVersion> publishedVerList);
	
}
