package com.sds.cmsapp.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class StatusLog {
	private int status_log_idx;
	private String comments;
	private String regdate;
	
	// 부모 테이블
	private Emp emp;
	private MasterCode masterCode;
	private Document document;
}
