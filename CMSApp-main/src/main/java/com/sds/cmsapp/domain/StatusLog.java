package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class StatusLog {
	private int statusLogIdx;
	private String comments;
	private Timestamp regdate;
	
	// 부모 테이블
	private Emp emp;
	private MasterCode masterCode;
	private Document document;
}
