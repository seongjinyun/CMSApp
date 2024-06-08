package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class DocumentVersion {
	private int document_version_idx;

	// 식별 관계
	private Document document;

	// 부모 테이블
	private VersionLog versionLog;
	
	//상태 코드
	private MasterCode status_code;
}
