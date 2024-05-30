package com.sds.cmsapp.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class VersionLog {
	private int version_log_idx;
	private VersionLog branched_version_log;
	private Emp emp;
	private Project project;
	private Folder folder;
	private Document document;
	private String version;
	private String title;
	private String content;
	private String comments;
	private Timestamp regdate;
}
