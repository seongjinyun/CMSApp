package com.sds.cmsapp.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class VersionLog {
	private int version_log_idx;

	private String version;
	private String title;
	private String content;
	private String comments;
	private String regdate;
	
	// 부모 테이블
	private Emp emp;
	private Document document;
	
	private VersionLog versionLog;
	
}
