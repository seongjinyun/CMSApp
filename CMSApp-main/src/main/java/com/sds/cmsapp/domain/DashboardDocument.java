package com.sds.cmsapp.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDocument {
	
	private int document_idx;
	private String version;
	private String title;
	private String emp_name;
	private String dept_name;
	private String role_name;
	private String regdate;
	private String comments;
	
	private int status_code;
	private String status_name;
	
	private int folder_idx;
	private String folder_name;
	private int project_idx;
	private String project_name;

}
