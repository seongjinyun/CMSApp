package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class Published_version {
	private int published_version_idx;
	private Document document;
	private VersionLog version_log;
	private PublishedVersionName published_version_name;
}
