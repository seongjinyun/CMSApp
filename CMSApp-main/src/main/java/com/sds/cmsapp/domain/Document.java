package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class Document {
	private int document_idx;
	private int hit;
	
//	// 부모 테이블 (외래키)
//	private Emp emp;
	
	// 자식 테이블
//	private List<StatusLog> statusLogList; // 상태 로그
//	private List<VersionLog> versionLogList; // 버전 로그
	
	private StatusLog latestRegisteredStatusLog;
}
