package com.sds.cmsapp.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class PublishedVersion {
	private int publishedVersionIdx;
	private Document document;
	private VersionLog versionLog;
	private PublishedVersionName publishedVersionName;
	
	@Builder
	public PublishedVersion(int publishedVersionIdx, Document document, VersionLog versionLog,
			PublishedVersionName publishedVersionName) {
		this.publishedVersionIdx = publishedVersionIdx;
		this.document = document;
		this.versionLog = versionLog;
		this.publishedVersionName = publishedVersionName;
	}
	
	
}
