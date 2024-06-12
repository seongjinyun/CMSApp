package com.sds.cmsapp.domain;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

// Setter가 필요하다
@Getter @Setter
public class Document {
	
	private int documentIdx;
	private int hit;
	
	// 참조키
	private Folder folder; // folder_name, project_idx (folder_idx)
	
	// DocumentMapper의 selectAllForDashboardList 수행 결과를 담기 위한 변
	
	
	
	// private Project project; // project_name (project_idx)
		
	//private StatusLog latestRegisteredStatusLog; // status_code, emp_idx, comments (document_idx)
	//private Emp emp; // emp_name, dept_idx (emp_idx)
	// private Dept dept; // dept_name (dept_idx)
	// private Role role; // role_name (role_code)
	// private MasterCode masterCode; // status_name (status_code)
	
	//private Trash trash; // trash_idx (document_idx)
	
	//private DocumentVersion documentVersion; // version_log_idx (document_idx)
	// private VersionLog versionLog; // version, title (version_log_idx)

	
}
