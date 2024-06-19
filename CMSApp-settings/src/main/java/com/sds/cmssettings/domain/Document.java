package com.sds.cmssettings.domain;

import lombok.Data;

@Data
public class Document {
	
	private int documentIdx;
	private int hit;
	
	private Emp emp;
	
	// 참조키
	private Folder folder; // folder_name, project_idx (folder_idx)
	
	// DocumentMapper의 selectAllForDashboardList 수행 결과를 담기 위한 변

	// 0603 추가
	private DocumentVersion documentVersion; // documentVersion이 version_log_idx를 가지고 있음...
	
	// 제목을 출력하기 위한 객체
	private VersionLog versionLog;
}
