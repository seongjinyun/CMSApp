package com.sds.cmssetting.domain;

import lombok.Data;

@Data
public class VersionLog {
	private int versionLogIdx;

	private String version;
	private String title;
	private String content;
	private String comments;
	private String regdate;
	
	// 부모 테이블
	private Emp emp;
	private Document document;
	
}
