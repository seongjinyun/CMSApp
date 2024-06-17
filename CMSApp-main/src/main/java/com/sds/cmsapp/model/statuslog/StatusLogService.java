package com.sds.cmsapp.model.statuslog;

import java.util.List;

import com.sds.cmsapp.domain.PublishedVersion;

public interface StatusLogService {
	
	//public StatusLog select(int documentIdx);
	
	//public void regist(StatusLog statusLog);
	
	public void registPublishedLog(List<PublishedVersion> publishedVerList, String comments);

	
}
