package com.sds.cmssetting.domain;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
public class DocumentVersion {
	private int documentVersionIdx;

	// 식별 관계
	private Document document;

	// 부모 테이블
	private VersionLog versionLog;
	
	// 상태 코드
	private MasterCode masterCode;
	
	// 상태 변경 사원
	private Emp emp;
	
	// 상태 변경 코멘트
	private String statusComments;
	
	// 상태 변경일자
	private Timestamp statusRegdate;
	
	public DocumentVersion() {
		
	}

	public DocumentVersion(Document document, MasterCode masterCode) {
		this.document = document;
		this.masterCode = masterCode;
	}
	
	public DocumentVersion(Document document, MasterCode masterCode, Emp emp, String statusComments) {
		this.document = document;
		this.masterCode = masterCode;
		this.emp = emp;
		this.statusComments = statusComments;
	}
	
	
	
}
