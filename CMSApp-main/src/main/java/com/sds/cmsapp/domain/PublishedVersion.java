package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class PublishedVersion {
	private int publishedVersionIdx;
	private Document document;
	private VersionLog versionLog;
	private PublishedVersionName publishedVersionName;
}
