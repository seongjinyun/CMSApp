package com.sds.cmsapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class ResponseDocumentCountDTO {
	private Integer inReview;
	private Integer reviewed;
	private Integer published;
	private Integer rejected;
	
}
