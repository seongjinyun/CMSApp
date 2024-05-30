package com.sds.cmsapp.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class StatusLog {
	private int status_log_idx;
	private Emp emp;
	private Document document;
	private StatusLog status_log;
	private String comments;
	private Timestamp regdate;
}
