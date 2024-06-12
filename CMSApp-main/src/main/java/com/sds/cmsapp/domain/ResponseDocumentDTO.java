package com.sds.cmsapp.domain;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class ResponseDocumentDTO {

	private int documentIdx;
	private String folderName;
	private String projectName;
	private String title;
	private String version;
	private int statusCode;
	private String statusName;
	private String empName;
	private String deptName;
	private String comments;
	private String regdate;

}
