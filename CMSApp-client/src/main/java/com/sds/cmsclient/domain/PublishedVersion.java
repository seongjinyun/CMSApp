package com.sds.cmsclient.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PublishedVersion {
	private int publishedVersionIdx;
	private Document document;
	private VersionLog versionLog;
	private PublishedVersionName publishedVersionName;
	
	public PublishedVersion(Document document, VersionLog versionLog) {
		this.document = document;
		this.versionLog = versionLog;
	}
	
	public PublishedVersion(Document document, VersionLog versionLog, PublishedVersionName publishedVersionName) {
		this.document = document;
		this.versionLog = versionLog;
		this.publishedVersionName = publishedVersionName;
	}
	
}
