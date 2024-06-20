package com.sds.cmssetting.domain;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ResponseDocDTO {

	private int documentIdx;
	private String statusName;
	private String version;
	private String title;
	private Integer folderIdx;
	private String folderName;
	private String projectName;
	private String regdate;
	private String empName;
	private String deptName;
	private String comments;

}
