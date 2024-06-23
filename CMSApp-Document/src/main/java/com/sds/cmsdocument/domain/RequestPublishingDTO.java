package com.sds.cmsdocument.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestPublishingDTO {
	String publishedVersionName;
	String comments;
	
	int empIdx;
	String roleName;
}
