package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class Trash {
	private Integer trash_idx;
	private Emp emp;
	private Document document;
	private String regdate;
	
	private VersionLog versionLog;
}
