package com.sds.cmssettings.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PublishedVersionName {
	private int publishedVersionNameIdx;
	private String publishedVersionName;
	
	public PublishedVersionName(String publishedVersionName) {
		this.publishedVersionName = publishedVersionName;
	}
	
}
