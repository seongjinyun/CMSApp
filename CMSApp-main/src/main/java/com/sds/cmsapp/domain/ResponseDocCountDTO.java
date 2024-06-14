package com.sds.cmsapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Builder
public class ResponseDocCountDTO {
	private Integer inReviewCount;
	private Integer reviewedCount;
	private Integer publishedCount;
	private Integer rejectedCount;
	
}
