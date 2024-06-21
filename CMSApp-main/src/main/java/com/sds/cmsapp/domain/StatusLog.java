package com.sds.cmsapp.domain;

import java.sql.Timestamp;

import lombok.Data;

// 상태 변경 내역을 기록하기 위한 독립 테이블
@Data
public class StatusLog {
	private int statusLogIdx;
	private int documentIdx;
	private int versionLogIdx;
	private int empIdx;
	private int statusCode;
	private String comments;
	private Timestamp regdate;
	
	public StatusLog(int documentIdx, int versionLogIdx, int empIdx, int statusCode, String comments) {
		super();
		this.documentIdx = documentIdx;
		this.versionLogIdx = versionLogIdx;
		this.empIdx = empIdx;
		this.statusCode = statusCode;
		this.comments = comments;
	}

}
