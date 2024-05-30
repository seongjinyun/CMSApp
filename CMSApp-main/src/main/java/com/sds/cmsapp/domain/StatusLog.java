package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class StatusLog {
	private int status_log_idx;
	private Emp emp;
	private Document document;
	private MasterCode masterCode;
	private String comments;
	private String regdate;
}
