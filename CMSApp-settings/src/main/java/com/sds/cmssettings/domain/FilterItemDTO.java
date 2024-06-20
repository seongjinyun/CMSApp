package com.sds.cmssettings.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class FilterItemDTO {
	
	private final List<Integer> statusCodeList;
	private final Timestamp startDate;
	private final Timestamp endDate;
	private final List<Integer> documentIdxListInProject;
	
}
