package com.sds.cmssettings.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
public class DocumentVersion {
	private int documentVersionIdx;

	// 식별 관계
	private Document document;

	// 부모 테이블
	private VersionLog versionLog;
	
	//상태 코드
	private MasterCode masterCode;
	
	public DocumentVersion() {
		
	}

	public DocumentVersion(Document document, MasterCode masterCode) {
		this.document = document;
		this.masterCode = masterCode;
	}
	
	
	
}
