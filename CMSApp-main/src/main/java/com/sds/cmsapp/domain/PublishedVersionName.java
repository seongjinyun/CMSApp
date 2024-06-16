package com.sds.cmsapp.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class PublishedVersionName {
	private int publishedVersionNameIdx;
	private String publishedVersionName;
	
	 @Builder
	public PublishedVersionName(int publishedVersionNameIdx, String publishedVersionName) {
		super();
		this.publishedVersionNameIdx = publishedVersionNameIdx;
		this.publishedVersionName = publishedVersionName;
	}
	
}
