package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class Document {
	private int documentIdx;
	private int hit;
	private Folder folder;
	
//	// 부모 테이블 (외래키)
	private Emp emp;
	
	// 자식 테이블
//	private List<StatusLog> statusLogList; // 상태 로그
//	private List<VersionLog> versionLogList; // 버전 로그
	
	private StatusLog latestRegisteredStatusLog;
	
	// 0603 추가
	private DocumentVersion documentVersion; // documentVersion이 version_log_idx를 가지고 있음...
}
