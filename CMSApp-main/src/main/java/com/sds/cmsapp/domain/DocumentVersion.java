package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class DocumentVersion {
	private int document_version_idx;
	private Document document;
	private VersionLog version_log;
}
