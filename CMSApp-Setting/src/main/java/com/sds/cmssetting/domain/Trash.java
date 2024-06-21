package com.sds.cmssetting.domain;

import lombok.Data;

@Data
public class Trash {
	private Integer trashIdx;
	private Emp emp;
	private Document document;
	private String regdate;
	
	// 테이블에 없는것
	private VersionLog versionLog;
}
