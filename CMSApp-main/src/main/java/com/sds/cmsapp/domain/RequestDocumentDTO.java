package com.sds.cmsapp.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


// Dashboard 메뉴에서 문서 목록을 조회할 때 사용할 필터 조건
@Getter @Setter
public final class RequestDocumentDTO {
	
	private List<Integer> statusCodeList;
	private Timestamp startDate;
	private Timestamp endDate;
	private List<Integer> projectIdxList;
	
}
