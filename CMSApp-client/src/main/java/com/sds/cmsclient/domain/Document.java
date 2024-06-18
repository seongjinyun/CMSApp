package com.sds.cmsclient.domain; 

import lombok.Data;

@Data
public class Document {
	
	private int documentIdx;
	private int hit;
	
	
	// 참조키
	private Folder folder; // folder_name, project_idx (folder_idx)
	
	// DocumentMapper의 selectAllForDashboardList 수행 결과를 담기 위한 변

	
	// 제목을 출력하기 위한 객체
	private VersionLog versionLog;
}
