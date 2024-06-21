package com.sds.cmssetting.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/* 문서 목록 조회에 사용되는 필터 조건 */ 
@Setter @Getter
public final class RequestDocFilterDTO {
	
	private List<String> statusList;
	private Timestamp startDate;
	private Timestamp endDate;
	private List<String> projectList;
	
}
