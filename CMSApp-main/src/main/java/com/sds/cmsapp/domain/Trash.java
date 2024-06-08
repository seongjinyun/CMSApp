package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class Trash {
	private Integer trashIdx;
	private Emp emp;
	private Document document;
	private String regdate;
	
	private VersionLog versionLog;
}
