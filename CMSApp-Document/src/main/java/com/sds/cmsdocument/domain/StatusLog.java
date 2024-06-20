package com.sds.cmsdocument.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class StatusLog {
	private int statusLogIdx;
	private String comments;
	private Timestamp regdate;
	
	// 부모 테이블
	private Document document;
	private Emp emp;
	private MasterCode masterCode;
	
	public StatusLog() {
		
	}
	
	public StatusLog(Emp emp, Document document, MasterCode masterCode, String comments) {
		this.emp = emp;
		this.document = document;
		this.masterCode = masterCode;
		this.comments = comments;
	}
	
}
